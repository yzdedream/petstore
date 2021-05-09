package com.kelvin.petstore.service;

import com.kelvin.petstore.dao.UserDao;
import com.kelvin.petstore.model.AppUser;
import com.kelvin.petstore.model.AppUserCreateForm;
import com.kelvin.petstore.model.AppUserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public long createUser(AppUserCreateForm user) {
        return this.userDao.createAppUser(user);
    }

    @Transactional(readOnly = true)
    public AppUser findUserById(long id) {
        return this.userDao.findUserById(id);
    }

    @Transactional(readOnly = true)
    public AppUser findUserByName(String name) {
        return this.userDao.findUserByName(name);
    }

    @Transactional
    public void updateUser(String username, AppUserUpdateForm form) {
        this.userDao.updateAppUser(username,form);
    }
}
