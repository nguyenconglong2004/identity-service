package com.nguyenconglong.identify_service.service;


import com.nguyenconglong.identify_service.dto.request.UserCreationRequest;
import com.nguyenconglong.identify_service.dto.response.UserResponse;
import com.nguyenconglong.identify_service.entity.User;
import com.nguyenconglong.identify_service.exception.AppException;
import com.nguyenconglong.identify_service.mapper.UserMapper;
import com.nguyenconglong.identify_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import  org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserServiceTest {

    private UserMapper userMapper;
    private UserCreationRequest request;
    private UserResponse response;
    private User user;
    private LocalDate dob;

    @Autowired
    private UserService userService;

    @MockBean
    UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void init(){
        dob = LocalDate.of(2004, 1, 15);
        request = UserCreationRequest.builder()
                .username("Username")
                .password("Password")
                .fullname("Full Name")
                .dob(dob)
                .build();
        response = UserResponse.builder()
                .id("id-12345")
                .username("Username")
                .dob(dob)
                .fullname("Full Name")
                .build();
        user = User.builder()
                .id("id-12345")
                .username("Username")
                .dob(dob)
                .fullname("Full Name")
                .password("Password")
                .build();
    }

    @Test
    void createUser_success(){
        //Given
        Mockito.when(userRepository.existsByUsername(anyString())).
                thenReturn(false);
        Mockito.when(userRepository.save(any()))
                .thenReturn(user);
        //When
        var result = userService.createUser(request);

        //Then
        org.assertj.core.api.Assertions.assertThat(result.getId()).isEqualTo(response.getId());
    }
    @Test
    void createUser_userExisted_fail(){
        //Given
        Mockito.when(userRepository.existsByUsername(anyString())).
                thenReturn(true);
        //When
        var exception = Assertions.assertThrows(AppException.class, () -> userService.createUser(request));

        org.assertj.core.api.Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);

    }

    @Test
    void getProfile_success(){
        Mockito.when(userRepository.findByUsername(anyString())).
                thenReturn(Optional.of(user));
         var result = userService.getProfile(user.getUsername());

        org.assertj.core.api.Assertions.assertThat(result.getId()).isEqualTo(user.getId());
    }
}
