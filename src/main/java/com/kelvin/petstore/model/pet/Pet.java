package com.kelvin.petstore.model.pet;

import java.util.List;

public class Pet {
    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;

    public Pet(){
        this.category = new Category();
    }
}
