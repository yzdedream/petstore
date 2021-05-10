package com.kelvin.petstore.util;

import com.kelvin.petstore.model.user.AppUserCreateForm;
import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserTestUtil {
    @Autowired
    Database db;

    public void clearUser(String username) {
        @SQL String sql = "delete from app_user where username = ?";
        this.db.withVoidTransaction(tx -> this.db.update(sql, username));
    }

    public AppUserCreateForm getTestUserCreateForm() {
        AppUserCreateForm user = new AppUserCreateForm();
        user.username = "kelvin";
        user.firstName = "kelvin";
        user.lastName = "K";
        user.email = "kelvin@K.com";
        user.phone = "12233";
        return user;
    }
}
