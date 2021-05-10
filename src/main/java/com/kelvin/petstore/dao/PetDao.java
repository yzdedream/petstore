package com.kelvin.petstore.dao;

import com.kelvin.petstore.model.pet.Category;
import com.kelvin.petstore.model.pet.Pet;
import com.kelvin.petstore.model.pet.Tag;
import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetDao {
    private final Database db;

    @Autowired
    public PetDao(Database db) {
        this.db = db;
    }

    public long createCategory(String name) {
        @SQL String sql = "INSERT INTO category(name)\n" +
                "VALUES (?)\n" +
                "RETURNING id;";
        return this.db.findUniqueInt(sql, name);
    }

    public void updateCategory(long id, String name) {
        @SQL String sql = "UPDATE category\n" +
                "SET name = ?\n" +
                "WHERE id = ?;\n";
        this.db.update(sql, name, id);
    }

    public List<Category> getCategories() {
        @SQL String sql = "SELECT id, name\n" +
                "FROM category;\n";
        return db.findAll(Category.class, sql);
    }

    public long createTag(String name) {
        @SQL String sql = "INSERT INTO tag(name)\n" +
                "VALUES (?)\n" +
                "RETURNING id;";
        return this.db.findUniqueInt(sql, name);
    }

    public void updateTag(long id, String name) {
        @SQL String sql = "UPDATE tag\n" +
                "SET name = ?\n" +
                "WHERE id = ?;\n";
        this.db.update(sql, name, id);
    }

    public long createPet(String name, long categoryId, String status) {
        @SQL String sql = "INSERT INTO pet (category_id, name, status)\n" +
                "VALUES (?, ?, ?)\n" +
                "RETURNING id" +
                ";";
        return this.db.findUniqueInt(sql, categoryId, name, status);
    }

    private final @SQL
    String petSQL = "SELECT pet.id,\n" +
            "       pet.name,\n" +
            "       pet.status,\n" +
            "       " +
            "c.id                                                             as \"category.id\",\n" +
            "       c.name                                                           as \"category.name\",\n" +
            "       array_agg(json_build_object('id', t.id, 'name', t.name)) as tags\n" +
            "FROM pet\n" +
            "       " +
            "  " +
            "LEFT JOIN category c on pet.category_id = c.id\n" +
            "         " +
            "LEFT JOIN pet_tag pt on pet.id = pt.pet_id\n" +
            "         " +
            "LEFT JOIN tag t on pt.tag_id = t.id\n";

    public List<Pet> getPets() {
        @SQL String sql = this.petSQL + "GROUP BY pet.id, c.id;";
        List<Pet> result = this.db.findAll(Pet.class, sql);
        result.forEach(pet -> {
            if (pet.tags.size() > 0 && pet.tags.get(0).isEmpty()) {
                pet.tags.clear();
            }
        });
        return result;
    }

    public Pet getPetById(long petId) {
        @SQL String sql = this.petSQL + "WHERE pet.id = ?\n GROUP BY pet.id, c.id;";
        Pet result = db.findUnique(Pet.class, sql, petId);
        if (result.tags.size() > 0 && result.tags.get(0).isEmpty()) {
            result.tags.clear();
        }
        return result;
    }

    public List<Pet> getPetsByStatus(String status) {
        @SQL String sql = this.petSQL + "WHERE pet.status = ?\n GROUP BY pet.id, c.id;";
        List<Pet> results = db.findAll(Pet.class, sql, status);
        this.removeEmptyTags(results);
        return results;
    }

    private void removeEmptyTags(List<Pet> pets) {
        pets.forEach(pet -> {
            if (pet.tags.size() > 0 && pet.tags.get(0).isEmpty()) {
                pet.tags.clear();
            }
        });
    }

    public List<Tag> getTags() {
        @SQL String sql = "SELECT id, name\n" +
                "FROM tag;";
        return this.db.findAll(Tag.class, sql);
    }

    public void setTagForPet(long petId, long tagId) {
        @SQL String sql = "INSERT INTO pet_tag (pet_id, tag_id)\n" +
                "VALUES (?, ?);";
        this.db.update(sql, petId, tagId);
    }

    public void updatePet(long petId, String name, long categoryId, String status) {
        @SQL String sql = "UPDATE pet\n" +
                "SET name        = ?,\n" +
                "    category_id = ?,\n" +
                "    " +
                "status=?\n" +
                "WHERE pet.id = ?;\n";
        db.update(sql, name, categoryId, status, petId);
    }

    public void updatePetStatus(long petId, String status) {
        @SQL String sql = "UPDATE pet\n" +
                "SET status = ?\n" +
                "WHERE id = ?";
        db.update(sql, status, petId);
    }

    public void clearTagsOfPet(long petId) {
        @SQL String sql = "DELETE\n" +
                "FROM pet_tag\n" +
                "WHERE pet_id = ?; \n";
        db.update(sql, petId);
    }

    public void removePetTag(long petId, long tagId) {
        @SQL String sql = "DELETE\n" +
                "FROM pet_tag\n" +
                "WHERE pet_id = ? && tag_id = ?;\n";
        db.update(sql, petId, tagId);
    }

    public void deletePet(long petId) {
        @SQL String sql = "DELETE\n" +
                "FROM pet\n" +
                "WHERE id = ?";
        db.update(sql, petId);
    }
}
