package com.kelvin.petstore.util;

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
}
