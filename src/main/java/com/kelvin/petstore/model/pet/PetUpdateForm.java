package com.kelvin.petstore.model.pet;

import java.util.List;

public class PetUpdateForm {
    public String name;
    public long categoryId;
    public String status;
    public List<Long> tagIDs;
}
