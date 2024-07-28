package com.nguyenconglong.identify_service.controller;

import com.nguyenconglong.identify_service.dto.request.ApiResponse;
import com.nguyenconglong.identify_service.dto.request.PermissionRequest;
import com.nguyenconglong.identify_service.dto.response.PermissionResponse;
import com.nguyenconglong.identify_service.service.PermissionService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@Data
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .results(permissionService.create(request))
                .build();
    }


    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .results(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name){
        permissionService.delete(name);
        return ApiResponse.<Void>builder().build();
    }
}
