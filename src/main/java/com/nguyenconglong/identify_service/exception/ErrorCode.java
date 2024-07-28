package com.nguyenconglong.identify_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_ERROR(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTED(1001, "Username existed", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1002, "Password is very short, it's must be more than {min} characters", HttpStatus.BAD_REQUEST ),
    USERNAME_INVALID(1003, "Username must be more than {min} characters", HttpStatus.BAD_REQUEST),
    USERID_NOT_EXISTED(1004, "UseID doesn't find", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1005, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED(1006, "Do not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1007, "Age must be more than {min}", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
