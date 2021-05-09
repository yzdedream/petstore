package com.kelvin.petstore.dao;

import com.kelvin.petstore.model.user.AppUser;
import com.kelvin.petstore.model.user.AppUserCreateForm;
import com.kelvin.petstore.model.user.AppUserUpdateForm;
import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final Database db;

    @Autowired
    public UserDao(Database db) {
        this.db = db;
    }

    @Nullable
    public AppUser findUserById(long id) {
        @SQL String sql = "SELECT id, username, first_name, last_name, email, phone, user_status\n" +
                "FROM app_user\n" +
                "WHERE id = ?; \n";
        return this.db.findUniqueOrNull(AppUser.class, sql, id);
    }

    @Nullable
    public AppUser findUserByName(String name) {
        @SQL String sql = "SELECT id, username, first_name, last_name, email, phone, user_status\n" +
                "FROM app_user\n" +
                "WHERE username = ?; \n";
        return this.db.findUniqueOrNull(AppUser.class, sql, name);
    }

    public long createAppUser(@NonNull AppUserCreateForm user) {
        @SQL String sql = "INSERT INTO app_user (first_name, last_name, username, email, phone)\n" +
                "VALUES (?, ?, ?, ?, ?)\n" +
                "RETURNING id";
        return db.findUniqueInt(sql,
                user.firstName, user.lastName, user.username, user.email, user.phone);
    }

    public void updateAppUser(String username, AppUserUpdateForm form) {
        @SQL String sql = "UPDATE app_user\n" +
                "SET first_name= ?,\n" +
                "    last_name=?,\n" +
                "    email=?,\n" +
                "    phone=?\n" +
                "WHERE username = ?;";
        db.update(sql, form.firstName, form.lastName, form.email, form.phone, username);
    }
}
