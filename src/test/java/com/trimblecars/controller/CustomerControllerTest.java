package com.trimblecars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trimblecars.dto.UserDTO;
import com.trimblecars.enums.Role;
import com.trimblecars.service.UserService;
import com.trimblecars.util.UserMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterCustomer() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("Test User");
        dto.setEmail("test@example.com");
        dto.setRole(Role.CUSTOMER);

        when(userService.registerUser(any())).thenReturn(UserMapper.toEntity(dto));

        mockMvc.perform(post("/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"));
    }
}
