package com.kelvin.petstore.model.user;

import javax.validation.constraints.NotNull;

public class AppUserUpdateForm {
    public String firstName;
    public String lastName;
    public String email;
    public String phone;

    @NotNull
    public String userStatus;
}
