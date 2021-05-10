package com.kelvin.petstore.util;

import com.kelvin.petstore.dao.PetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PetTestUtil {
    private final PetDao petDao;

    @Autowired
    public PetTestUtil(PetDao petDao) {
        this.petDao = petDao;
    }

    public long initTestPet() {
        String name = "doge";
        long categoryId = this.petDao.createCategory("dog");
        String status = "available";

        return this.petDao.createPet(name, categoryId, status);
    }
}
