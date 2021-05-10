package com.kelvin.petstore.controller;

import com.kelvin.petstore.exception.ResourceNotFoundException;
import com.kelvin.petstore.model.ApiResponse;
import com.kelvin.petstore.model.user.AppUser;
import com.kelvin.petstore.model.user.AppUserCreateForm;
import com.kelvin.petstore.model.user.AppUserUpdateForm;
import com.kelvin.petstore.service.UserService;
import com.kelvin.petstore.util.ApiResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping({"/createWithList", "/createWithArray"})
    public ApiResponse createWithList(@RequestBody List<AppUserCreateForm> forms) {
        this.userService.createWithList(forms);
        return ApiResponseUtil.buildDefaultResponse();
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
    public ApiResponse updateUser(@RequestBody @Valid AppUserUpdateForm form,
                                  @PathVariable String username) {
        if (!this.userService.isUserExist(username)) {
            throw new ResourceNotFoundException();
        }
        this.userService.updateUser(username, form);
        return ApiResponseUtil.buildDefaultResponse();
    }

    @DeleteMapping("/{username}")
    public ApiResponse deleteUser(@PathVariable String username) {
        if (!this.userService.isUserExist(username)) {
            throw new ResourceNotFoundException();
        }
        this.userService.deleteUser(username);
        return ApiResponseUtil.buildDefaultResponse();
    }
}
