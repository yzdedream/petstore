package com.kelvin.petstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kelvin.petstore.config.WebUnitTestConfiguration;
import com.kelvin.petstore.model.user.AppUserCreateForm;
import com.kelvin.petstore.model.user.AppUserUpdateForm;
import com.kelvin.petstore.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void testCreateWithList() throws Exception {
        this.userTestUtil.clearUser("kelvin");
        AppUserCreateForm user = this.userTestUtil.getTestUserCreateForm();
        List<AppUserCreateForm> userList = new ArrayList<>();
        userList.add(user);

        String json = objectMapper.writeValueAsString(userList);
        ResultActions result = this.mvc.perform(post("/user/createWithList")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        this.expectDefaultSuccessMessage(result);
    }

    private void expectDefaultSuccessMessage(ResultActions result) throws Exception {
        result.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("code").value(0))
                .andExpect(jsonPath("type").value("default"))
                .andExpect(jsonPath("message").value("successful operation"));
    }

    @Test
    @Transactional
    public void testCreateAndGetUser() throws Exception {
        this.userTestUtil.clearUser("kelvin");
        AppUserCreateForm user = this.userTestUtil.getTestUserCreateForm();
        String json = objectMapper.writeValueAsString(user);

        ResultActions result = mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        this.expectDefaultSuccessMessage(result);

        mvc.perform(get("/user/kelvin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("username").value("kelvin"))
                .andExpect(jsonPath("firstName").value("kelvin"))
                .andExpect(jsonPath("lastName").value("K"))
                .andExpect(jsonPath("email").value("kelvin@K.com"))
                .andExpect(jsonPath("phone").value("12233"));
    }

    @Test
    @Transactional
    public void testUpdateUser() throws Exception {
        this.userTestUtil.clearUser("kelvin");
        AppUserCreateForm user = this.userTestUtil.getTestUserCreateForm();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        AppUserUpdateForm form = new AppUserUpdateForm();
        form.firstName = "kelvin2";
        form.lastName = "K2";
        form.email = "kelvin2@K2.com";
        form.phone = "1223";
        form.userStatus = "banned";

        String updateJson = objectMapper.writeValueAsString(form);
        ResultActions result = mvc.perform(put("/user/kelvin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));
        this.expectDefaultSuccessMessage(result);

        // 400 for invalid user

    }

    @Test
    @Transactional
    public void testInvalidUpdate() throws Exception {
        this.userTestUtil.clearUser("kelvin");
        AppUserCreateForm user = this.userTestUtil.getTestUserCreateForm();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        AppUserUpdateForm form = new AppUserUpdateForm();
        form.userStatus = null;
        String updateJson = objectMapper.writeValueAsString(form);

        mvc.perform(put("/user/kelvin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isBadRequest());


        form.userStatus = "active";
        updateJson = objectMapper.writeValueAsString(form);
        mvc.perform(put("/user/nonexistent")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testInvalidDelete() throws Exception {
        mvc.perform(delete("/user/wrongname"))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        this.userTestUtil.clearUser("kelvin");
        AppUserCreateForm user = this.userTestUtil.getTestUserCreateForm();
        String json = objectMapper.writeValueAsString(user);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        ResultActions result = mvc.perform(delete("/user/kelvin"));
        this.expectDefaultSuccessMessage(result);
    }
}
