package com.nguyenconglong.identify_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nguyenconglong.identify_service.dto.request.UserCreationRequest;
import com.nguyenconglong.identify_service.dto.response.UserResponse;
import com.nguyenconglong.identify_service.exception.AppException;
import com.nguyenconglong.identify_service.exception.ErrorCode;
import com.nguyenconglong.identify_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc// help create mock request
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private UserCreationRequest request;
    private UserResponse response;
    private LocalDate dob;

    @MockBean
    private UserService userService;

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
    }

    @Test
    void createUser_success() throws Exception {
        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String content = objectMapper.writeValueAsString(request);
        Mockito.when(userService.createUser(ArgumentMatchers.any()))
                .thenReturn(response);
        //When
        mockMvc.perform(MockMvcRequestBuilders
                .post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                //Then
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));

    }
    @Test
    void createUser_invalidUsername_fail() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        request = UserCreationRequest.builder()
                .username("ngu")
                .password("Password")
                .fullname("Full Name")
                .dob(dob)
                .build();
        String content = objectMapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1003))
                .andExpect(MockMvcResultMatchers.jsonPath("message"). value("Username must be more than 4 characters"));

    }
}
