package com.kelvin.petstore.controller;

import com.kelvin.petstore.model.ApiResponse;
import com.kelvin.petstore.model.AppUser;
import com.kelvin.petstore.model.AppUserCreateForm;
import com.kelvin.petstore.model.AppUserUpdateForm;
import com.kelvin.petstore.service.UserService;
import com.kelvin.petstore.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public AppUser findUserByName(@PathVariable String username) {
        return this.userService.findUserByName(username);
    }

    @PostMapping("")
    public ApiResponse createUser(@RequestBody AppUserCreateForm user) {
        this.userService.createUser(user);
        return ApiResponseUtil.buildDefaultResponse();
    }

    @PutMapping("/{username}")
    public void updateUser(@RequestBody AppUserUpdateForm form, @PathVariable String username) {
        this.userService.updateUser(username, form);
    }

}
