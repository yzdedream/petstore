package com.kelvin.petstore.controller;

import com.kelvin.petstore.model.pet.*;
import com.kelvin.petstore.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@CrossOrigin(origins = "http://localhost:4200")
public class PetController {
    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/category")
    public long createCategory(@RequestBody String name) {
        return this.petService.createCategory(name);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return this.petService.getCategories();
    }

    @PostMapping("/tag")
    public long createTag(@RequestBody String name) {
        return this.petService.createTag(name);
    }

    @GetMapping("/tags")
    public List<Tag> getTags() {
        return this.petService.getTags();
    }

    @PostMapping("")
    public long createPet(@RequestBody PetCreateForm form) {
        return this.petService.createPet(form);
    }

    @GetMapping("")
    public List<Pet> getPets() {
        return this.petService.getPets();
    }

    @PutMapping("/{petId}")
    public void updatePet(@RequestBody PetUpdateForm form, @PathVariable long petId) {
        this.petService.updatePet(petId, form);
    }

    @DeleteMapping("/{petId}")
    public void deletePet(@PathVariable long petId) {
        this.petService.deletePet(petId);
    }
}
