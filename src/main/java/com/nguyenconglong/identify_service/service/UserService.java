package com.nguyenconglong.identify_service.service;

import com.nguyenconglong.identify_service.dto.request.UserCreationRequest;
import com.nguyenconglong.identify_service.dto.request.UserUpdateRequest;
import com.nguyenconglong.identify_service.dto.response.UserResponse;
import com.nguyenconglong.identify_service.entity.User;
import com.nguyenconglong.identify_service.exception.AppException;
import com.nguyenconglong.identify_service.exception.ErrorCode;
import com.nguyenconglong.identify_service.mapper.UserMapper;
import com.nguyenconglong.identify_service.repository.RoleRepository;
import com.nguyenconglong.identify_service.repository.UserRepository;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(!CollectionUtils.isEmpty(request.getRoles())){
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostAuthorize("returnObject.username == authentication.name || hasRole('ADMIN')")
    public UserResponse getUser(String id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_EXISTED)));
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_EXISTED));
        if(request.getPassword() != null) {
            log.info("new password: " + request.getPassword());
            user.setPassword(request.getPassword());
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        if(request.getFullname() != null)
            user.setFullname(request.getFullname());
        if(request.getDob() != null)
            user.setDob(request.getDob());
        if(!request.getRoles().isEmpty()) {
            var roles = roleRepository.findAllById(request.getRoles());
            user.setRoles(new HashSet<>(roles));
        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void deleteUser(String userId){
        userRepository.deleteById(userId);
    }

    public UserResponse getProfile(String username){
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERID_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }
}
