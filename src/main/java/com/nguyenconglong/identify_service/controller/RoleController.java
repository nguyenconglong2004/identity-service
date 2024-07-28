package com.nguyenconglong.identify_service.controller;

import com.nguyenconglong.identify_service.dto.request.ApiResponse;
import com.nguyenconglong.identify_service.dto.request.PermissionRequest;
import com.nguyenconglong.identify_service.dto.request.RoleRequest;
import com.nguyenconglong.identify_service.dto.request.RoleUpdateRequest;
import com.nguyenconglong.identify_service.dto.response.PermissionResponse;
import com.nguyenconglong.identify_service.dto.response.RoleResponse;
import com.nguyenconglong.identify_service.entity.Role;
import com.nguyenconglong.identify_service.service.PermissionService;
import com.nguyenconglong.identify_service.service.RoleService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .results(roleService.create(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .results(roleService.getAll())
                .build();
    }

    @PutMapping("/{name}")
    ApiResponse<RoleResponse> update(@PathVariable("name") String name, @RequestBody RoleUpdateRequest request){
        return ApiResponse.<RoleResponse>builder()
                .results(roleService.update(name, request))
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable("name") String name){
        roleService.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
