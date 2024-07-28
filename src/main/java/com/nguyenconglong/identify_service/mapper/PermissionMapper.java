package com.nguyenconglong.identify_service.mapper;


import com.nguyenconglong.identify_service.dto.request.PermissionRequest;
import com.nguyenconglong.identify_service.dto.response.PermissionResponse;
import com.nguyenconglong.identify_service.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}

