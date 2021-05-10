package com.kelvin.petstore.controller;

import com.kelvin.petstore.model.pet.Pet;
import com.kelvin.petstore.model.shop.Order;
import com.kelvin.petstore.model.shop.OrderCreateForm;
import com.kelvin.petstore.service.OrderService;
import com.kelvin.petstore.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@CrossOrigin(origins = "http://localhost:4200")
public class ShopController {
    private final OrderService orderService;
    private final PetService petService;

    @Autowired
    public ShopController(OrderService orderService,
                          PetService petService) {
        this.orderService = orderService;
        this.petService = petService;
    }

    @PostMapping("/order")
    public long createOrder(@RequestBody OrderCreateForm form) {
        return this.orderService.placeOrder(form.petId, form.quantity);
    }

    @GetMapping("/inventory")
    public List<Pet> getInventory() {
        return this.petService.getPetsByStatus("available");
    }

    @GetMapping("/orders")
    public List<Order> getOrders() {
        return this.orderService.getOrders();
    }

    @DeleteMapping("/order/{orderId}")
    public void deleteOrder(@PathVariable long orderId) {
        this.orderService.deleteOrder(orderId);
    }
}
