package com.trimblecars.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trimblecars.dto.UserDTO;
import com.trimblecars.enums.Role;
import com.trimblecars.service.CarService;
import com.trimblecars.service.LeaseService;
import com.trimblecars.service.UserService;
import com.trimblecars.util.UserMapper;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SuppressWarnings("removal")
    @MockBean
    private UserService userService;

    @SuppressWarnings("removal")
    @MockBean
    private CarService carService;

    @SuppressWarnings("removal")
    @MockBean
    private LeaseService leaseService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        UserDTO dto = new UserDTO();
        dto.setName("Admin");
        dto.setEmail("admin@trimble.com");
        dto.setRole(Role.ADMIN);

        when(userService.registerUser(any())).thenReturn(UserMapper.toEntity(dto));

        mockMvc.perform(post("/admin/register-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin"))
                .andExpect(jsonPath("$.role").value("ADMIN"));
    }

    @Test
    void testGetUsersByRole() throws Exception {
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setName("Cust");
        user1.setEmail("c@t.com");
        user1.setRole(Role.CUSTOMER);

        when(userService.getUsersByRole(Role.CUSTOMER)).thenReturn(List.of(UserMapper.toEntity(user1)));

        mockMvc.perform(get("/admin/users/CUSTOMER"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].role").value("CUSTOMER"));
    }
}
