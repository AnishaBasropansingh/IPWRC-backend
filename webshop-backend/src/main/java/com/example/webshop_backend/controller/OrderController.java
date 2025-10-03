package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.OrderDAO;
import com.example.webshop_backend.dao.ProductDAO;
import com.example.webshop_backend.dto.OrderDTO;
import com.example.webshop_backend.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
class OrderController {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderController(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.createOrder(orderDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Order>> getAllOrders() {
        List<Order> orders = (List<Order>) this.orderDAO.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(name = "id") Long order_id) {
        Optional<Order> order = this.orderDAO.getOrderById(order_id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
