package com.nguyenconglong.identify_service.mapper;


import com.nguyenconglong.identify_service.dto.request.UserCreationRequest;
import com.nguyenconglong.identify_service.dto.request.UserUpdateRequest;
import com.nguyenconglong.identify_service.dto.response.UserResponse;
import com.nguyenconglong.identify_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "roles", ignore = true)
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

}
