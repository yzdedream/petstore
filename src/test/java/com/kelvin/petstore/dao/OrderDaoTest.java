package com.kelvin.petstore.dao;

import com.kelvin.petstore.model.shop.Order;
import com.kelvin.petstore.util.PetTestUtil;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class OrderDaoTest {
    @Autowired
    private Flyway flyway;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PetTestUtil petTestUtil;

    @BeforeEach
    public void cleanDB() {
        flyway.clean();
        flyway.migrate();
    }

    @Test
    @Transactional
    public void testCreateAndGetOrder() {
        long petId = this.petTestUtil.initTestPet();
        long orderId = this.orderDao.createOrder(petId, 1);
        Order order = this.orderDao.findOrderById(orderId);

        Assertions.assertEquals(petId, order.petId);
        Assertions.assertEquals(1, order.quantity);
        Assertions.assertEquals("placed", order.status);
        Assertions.assertFalse(order.complete);
        Assertions.assertNull(order.shipDate);
    }

    @Test
    @Transactional
    public void testDeleteOrder() {
        long petId = this.petTestUtil.initTestPet();
        long orderId = this.orderDao.createOrder(petId, 1);
        int orderCount = this.orderDao.getOrders().size();
        Assertions.assertEquals(1, orderCount);
        this.orderDao.deleteOrder(orderId);

        orderCount = this.orderDao.getOrders().size();
        Assertions.assertEquals(0, orderCount);
    }

    @Test
    @Transactional
    public void testShipOrder() {
        long petId = this.petTestUtil.initTestPet();
        long orderId = this.orderDao.createOrder(petId, 1);
        Order order = this.orderDao.findOrderById(orderId);

        Assertions.assertNull(order.shipDate);
        this.orderDao.shipOrder(orderId);
        order = this.orderDao.findOrderById(orderId);
        Assertions.assertNotNull(order.shipDate);
        Assertions.assertEquals("shipped", order.status);
    }
}
