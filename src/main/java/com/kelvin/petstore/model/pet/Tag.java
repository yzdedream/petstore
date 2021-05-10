package com.kelvin.petstore.model.pet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONObject;
import org.postgresql.util.PGobject;

public class Tag {
    public long id;
    public String name;

    private boolean isEmpty = false;

    public Tag() {

    }

    public Tag(PGobject obj) {
        this(obj.getValue());
    }

    public Tag(String json) {
        JSONObject obj = new JSONObject(json);
        if (obj.isNull("id")) {
            this.id = -1;
            this.name = "";
            this.isEmpty = true;
        } else {
            this.id = obj.getInt("id");
            this.name = obj.getString("name");
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return this.isEmpty;
    }
}
