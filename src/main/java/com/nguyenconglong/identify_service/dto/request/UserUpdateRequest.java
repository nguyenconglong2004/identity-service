package com.nguyenconglong.identify_service.dto.request;

import com.nguyenconglong.identify_service.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;
    String fullname;

    @DobConstraint(min = 10, message = "INVALID_DOB")
    LocalDate dob;
    List<String> roles;
}
