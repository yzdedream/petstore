package com.kelvin.petstore.model.pet;

import java.util.List;

public class PetCreateForm {
    public long categoryId;
    public String name;
    public String status;
    public List<Long> tagIDs;
}
