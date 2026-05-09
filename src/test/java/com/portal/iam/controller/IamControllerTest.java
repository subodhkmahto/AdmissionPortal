package com.portal.iam.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portal.iam.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Triggers H2 DB
public class IamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetStatus_Returns200() throws Exception {
        mockMvc.perform(get("/api/iam/status"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Running"));
    }

    @Test
    public void testRegisterUser_MissingEmailTriggersGlobalException400() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("new_candidate");
        req.setPassword("testpassword123");
        // Intentionally leaving Email blank to test the Validation + GlobalExceptionHandler

        mockMvc.perform(post("/api/iam/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    public void testRegisterUser_ValidRequestReturns200() throws Exception {
        RegisterRequest req = new RegisterRequest();
        req.setUsername("johndoe");
        req.setEmail("johndoe@example.com");
        req.setPassword("securePassword!");

        mockMvc.perform(post("/api/iam/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
    }
}
