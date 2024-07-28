package com.nguyenconglong.identify_service.controller;

import com.nguyenconglong.identify_service.dto.request.*;
import com.nguyenconglong.identify_service.dto.response.AuthenticationResponse;
import com.nguyenconglong.identify_service.dto.response.IntrospectResponse;
import com.nguyenconglong.identify_service.service.AuthenticationService;
import com.nguyenconglong.identify_service.service.UserService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request){
        AuthenticationResponse result = authenticationService.authenticated(request);
        return  ApiResponse.<AuthenticationResponse>builder()
                .results(result)
                .build();
    }

    @PostMapping("/token")
    ApiResponse<IntrospectResponse> login(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);
        return  ApiResponse.<IntrospectResponse>builder()
                .results(result)
                .build();
    }

    @PostMapping("/log-out")
    ApiResponse<Void> logout (@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);
        return ApiResponse.<Void>builder()
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request)
            throws ParseException, JOSEException {
        AuthenticationResponse result = authenticationService.refreshToken(request);
        return  ApiResponse.<AuthenticationResponse>builder()
                .results(result)
                .build();
    }

}
