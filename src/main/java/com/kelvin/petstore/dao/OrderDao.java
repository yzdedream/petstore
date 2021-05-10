package com.kelvin.petstore.dao;

import com.kelvin.petstore.model.shop.Order;
import org.dalesbred.Database;
import org.dalesbred.annotation.SQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao {
    private final Database db;

    @Autowired
    OrderDao(Database db) {
        this.db = db;
    }

    public long createOrder(long petId, int quantity) {
        @SQL String sql = "INSERT INTO pet_order(pet_id, quantity, status, complete)\n" +
                "VALUES" +
                " (?, ?, 'placed', false)\n" +
                "RETURNING id;";
        return this.db.findUniqueInt(sql, petId, quantity);
    }

    public void updateOrder(long id, int quantity, String status, boolean complete) {
        @SQL String sql = "UPDATE pet_order\n" +
                "SET quantity =?,\n" +
                "    " +
                "status   = ?,\n" +
                "    complete=?\n" +
                "WHERE id = ?;\n";
        this.db.update(sql, quantity, status, complete);
    }

    public void shipOrder(long id) {
        @SQL String sql = "UPDATE pet_order\n" +
                "SET ship_date = (current_timestamp),\n" +
                "    " +
                "status    = 'shipped'\n" +
                "WHERE id = ?;";
        this.db.update(sql, id);
    }

    public void deleteOrder(long id) {
        @SQL String sql = "DELETE\n" +
                "FROM pet_order\n" +
                "WHERE id = ?;";
        this.db.update(sql, id);
    }

    public List<Order> getOrders(){
        @SQL String sql = "SELECT id, pet_id, quantity, ship_date, status, complete\n" +
                "FROM pet_order;";
        return this.db.findAll(Order.class, sql);
    }

    public Order findOrderById(long id) {
        @SQL String sql = "SELECT id, pet_id, quantity, ship_date, status, complete\n" +
                "FROM pet_order\n" +
                "WHERE id = ?" +
                ";";
        return this.db.findUnique(Order.class, sql, id);
    }
}
