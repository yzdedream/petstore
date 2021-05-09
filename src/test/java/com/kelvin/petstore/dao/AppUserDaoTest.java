package com.kelvin.petstore.dao;

import com.kelvin.petstore.model.user.AppUser;
import com.kelvin.petstore.model.user.AppUserCreateForm;
import com.kelvin.petstore.model.user.AppUserUpdateForm;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class AppUserDaoTest {
    @Autowired
    private Flyway flyway;
    @Autowired
    private UserDao userDao;

    @BeforeEach
    public void cleanDB() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Transactional
    public void testCreateAndGetUser() {
        AppUserCreateForm form = this.getDummyCreateForm();

        long id = this.userDao.createAppUser(form);
        AppUser created = this.userDao.findUserById(id);

        this.assertUserMatchCreateForm(form, created);
    }

    @Test
    @Transactional
    public void testFindUserByName() {
        AppUserCreateForm form = this.getDummyCreateForm();

        long id = this.userDao.createAppUser(form);
        AppUser created = this.userDao.findUserByName(form.username);

        this.assertUserMatchCreateForm(form, created);
    }

    private AppUserCreateForm getDummyCreateForm() {
        AppUserCreateForm form = new AppUserCreateForm();
        form.username = "kelvin";
        form.firstName = "kelvin";
        form.lastName = "K";
        form.email = "kelvin@K.com";
        form.phone = "112233";
        return form;
    }

    private void assertUserEqual(AppUser expected, AppUser actual) {
        assert actual != null;
        Assertions.assertEquals(expected.username, actual.username);
        Assertions.assertEquals(expected.firstName, actual.firstName);
        Assertions.assertEquals(expected.lastName, actual.lastName);
        Assertions.assertEquals(expected.email, actual.email);
        Assertions.assertEquals(expected.phone, actual.phone);
        Assertions.assertEquals(expected.userStatus, actual.userStatus);
    }

    private void assertUserMatchCreateForm(AppUserCreateForm form, AppUser user) {
        AppUser expected = new AppUser();
        expected.username = form.username;
        expected.firstName = form.firstName;
        expected.lastName = form.lastName;
        expected.email = form.email;
        expected.phone = form.phone;
        expected.userStatus = "active";
        this.assertUserEqual(expected, user);
    }

    @Test
    @Transactional
    public void testGetInvalidUser() {
        AppUser user = this.userDao.findUserById(-1);
        assert user == null;

        AppUser user2 = this.userDao.findUserByName("nonexistent");
        assert user2 == null;

        AppUser user3 = this.userDao.findUserByName(null);
        assert user3 == null;
    }

    @Test
    @Transactional
    public void testUpdateUser() {
        AppUserCreateForm form = this.getDummyCreateForm();
        long id = this.userDao.createAppUser(form);
        AppUser created = this.userDao.findUserByName(form.username);

        AppUserUpdateForm updateForm = new AppUserUpdateForm();
        updateForm.firstName = "kelvin2";
        updateForm.lastName = "K2";
        updateForm.email = "kelvin2@K2.com";
        updateForm.phone = "23333";

        assert created != null;
        this.userDao.updateAppUser(created.username, updateForm);
        AppUser updated = this.userDao.findUserById(id);

        assert updated != null;
        Assertions.assertEquals("kelvin2", updated.firstName);
        Assertions.assertEquals("K2", updated.lastName);
        Assertions.assertEquals("kelvin2@K2.com", updated.email);
        Assertions.assertEquals("23333", updated.phone);
    }
}
