package com.kelvin.petstore.dao;

import com.kelvin.petstore.model.pet.Category;
import com.kelvin.petstore.model.pet.Pet;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class PetDaoTest {
    @Autowired
    private Flyway flyway;

    @Autowired
    private PetDao petDao;

    @BeforeEach
    public void cleanDB() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Transactional
    public void testCreateCategory() {
        String name = "dog";
        this.petDao.createCategory(name);
        List<Category> categories = this.petDao.getCategories();
        Assertions.assertEquals(1, categories.size());

        Category c = categories.get(0);
        Assertions.assertEquals(name, c.name);
    }

    @Test
    @Transactional
    public void testCreateAndGetPet() {
        long petId = this.initTestPet();
        Pet pet = this.petDao.getPetById(petId);

        Assertions.assertEquals("doge", pet.name);
        Assertions.assertEquals("dog", pet.category.name);
        Assertions.assertEquals("available", pet.status);
    }

    private long initTestPet() {
        String name = "doge";
        long categoryId = this.petDao.createCategory("dog");
        String status = "available";

        return this.petDao.createPet(name, categoryId, status);
    }

    @Test
    @Transactional
    public void testAddTagsToPet() {
        long petId = this.initTestPet();
        String tag1 = "grey";
        String tag2 = "cute";
        long tagID1 = this.petDao.createTag(tag1);
        long tagID2 = this.petDao.createTag(tag2);

        this.petDao.setTagForPet(petId, tagID1);
        this.petDao.setTagForPet(petId, tagID2);

        Pet pet = this.petDao.getPetById(petId);
        Assertions.assertEquals("grey", pet.tags.get(0).name);
        Assertions.assertEquals("cute", pet.tags.get(1).name);
    }

    @Test
    @Transactional
    public void testUpdatePet() {
        long petId = this.initTestPet();
        Pet pet = this.petDao.getPetById(petId);
        String newCategoryName = "coin";
        long categoryId = this.petDao.createCategory(newCategoryName);
        this.petDao.updatePet(petId, pet.name, categoryId, pet.status);

        pet = this.petDao.getPetById(petId);
        Assertions.assertEquals(newCategoryName, pet.category.name);
    }
}
