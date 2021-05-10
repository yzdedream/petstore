package com.kelvin.petstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelvin.petstore.config.WebUnitTestConfiguration;
import com.kelvin.petstore.model.user.AppUserCreateForm;
import com.kelvin.petstore.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest
@ContextConfiguration(classes = {WebUnitTestConfiguration.class})
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserTestUtil userTestUtil;

    @Test
    @Transactional
    public void testCreateAndGetUser() throws Exception {
        this.userTestUtil.clearUser("kelvin");
        AppUserCreateForm user = this.userTestUtil.getTestUserCreateForm();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("code").value(0))
                .andExpect(jsonPath("type").value("default"))
                .andExpect(jsonPath("message").value("successful operation"));

        mvc.perform(get("/user/kelvin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("kelvin"))
                .andExpect(jsonPath("firstName").value("kelvin"))
                .andExpect(jsonPath("lastName").value("K"))
                .andExpect(jsonPath("email").value("kelvin@K.com"))
                .andExpect(jsonPath("phone").value("12233"));
    }
}
