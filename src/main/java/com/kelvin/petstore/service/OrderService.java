package com.kelvin.petstore.service;

import com.kelvin.petstore.dao.OrderDao;
import com.kelvin.petstore.dao.PetDao;
import com.kelvin.petstore.model.shop.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderDao orderDao;
    private final PetDao petDao;

    @Autowired
    OrderService(OrderDao orderDao, PetDao petDao) {
        this.orderDao = orderDao;
        this.petDao = petDao;
    }

    @Transactional
    public long placeOrder(long petId, int quantity) {
        long order = this.orderDao.createOrder(petId, quantity);
        this.petDao.updatePetStatus(petId, "reserved");
        return order;
    }

    @Transactional
    public List<Order> getOrders() {
        return this.orderDao.getOrders();
    }

    @Transactional
    public void deleteOrder(long orderId) {
        this.orderDao.deleteOrder(orderId);
    }
}
