package com.kelvin.petstore.service;

import com.kelvin.petstore.dao.UserDao;
import com.kelvin.petstore.model.user.AppUser;
import com.kelvin.petstore.model.user.AppUserCreateForm;
import com.kelvin.petstore.model.user.AppUserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void createWithList(List<AppUserCreateForm> forms) {
        forms.forEach(this.userDao::createAppUser);
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
        this.userDao.updateAppUser(username, form);
    }

    @Transactional(readOnly = true)
    public boolean isUserExist(String name) {
        return this.userDao.findUserByName(name) != null;
    }

    @Transactional
    public void deleteUser(String name) {
        this.userDao.deleteUser(name);
    }
}
