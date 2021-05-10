package com.kelvin.petstore.service;

import com.kelvin.petstore.dao.PetDao;
import com.kelvin.petstore.model.pet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {
    private final PetDao petDao;

    @Autowired
    PetService(PetDao petDao) {
        this.petDao = petDao;
    }

    @Transactional
    public long createCategory(String name) {
        return this.petDao.createCategory(name);
    }

    @Transactional(readOnly = true)
    public List<Category> getCategories() {
        return this.petDao.getCategories();
    }

    @Transactional
    public long createTag(String name) {
        return this.petDao.createTag(name);
    }

    @Transactional
    public List<Tag> getTags() {
        return this.petDao.getTags();
    }

    @Transactional
    public long createPet(PetCreateForm form) {
        long petId = this.petDao.createPet(form.name, form.categoryId, form.status);
        if (form.tagIDs != null) {
            form.tagIDs.forEach(tagId -> petDao.setTagForPet(petId, tagId));
        }
        return petId;
    }

    @Transactional
    public List<Pet> getPets() {
        return this.petDao.getPets();
    }

    @Transactional
    public void updatePet(long petId, PetUpdateForm form) {
        this.petDao.updatePet(petId, form.name, form.categoryId, form.status);
        if (form.tagIDs != null
                && form.tagIDs.size() > 0) {
            this.petDao.clearTagsOfPet(petId);
            form.tagIDs.forEach(tagId -> this.petDao.setTagForPet(petId, tagId));
        }
    }

    @Transactional
    public void deletePet(long petId) {
        this.petDao.deletePet(petId);
    }

    @Transactional(readOnly = true)
    public List<Pet> getPetsByStatus(String status) {
        return this.petDao.getPetsByStatus(status);
    }
}
