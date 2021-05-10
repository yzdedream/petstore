package com.kelvin.petstore.model.shop;

import java.sql.Timestamp;

public class Order {
    public long id;
    public long petId;
    public int quantity;
    public Timestamp shipDate;
    public String status;
    public boolean complete;
}
