package com.nguyenconglong.identify_service.service;


import com.nguyenconglong.identify_service.dto.request.RoleRequest;
import com.nguyenconglong.identify_service.dto.request.RoleUpdateRequest;
import com.nguyenconglong.identify_service.dto.response.RoleResponse;
import com.nguyenconglong.identify_service.entity.Role;
import com.nguyenconglong.identify_service.exception.AppException;
import com.nguyenconglong.identify_service.exception.ErrorCode;
import com.nguyenconglong.identify_service.mapper.RoleMapper;
import com.nguyenconglong.identify_service.repository.PermissionRepository;
import com.nguyenconglong.identify_service.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);

        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }

    public RoleResponse update(String name, RoleUpdateRequest request){
        Role role = roleRepository.findById(name)
                .orElseThrow(RuntimeException::new);
        if(request.getDescription() != null)
            role.setDescription(request.getDescription());
        if(!request.getPermissions().isEmpty()){
            var permissions = permissionRepository.findAllById(request.getPermissions());
            role.setPermissions(new HashSet<>(permissions));
        }

        return roleMapper.toRoleResponse(roleRepository.save(role));
    }

    public void delete(String name){
        roleRepository.deleteById(name);
    }
}
