package com.example.webshop_backend.controller;

import com.example.webshop_backend.dao.OrderDAO;
import com.example.webshop_backend.dao.ProductDAO;
import com.example.webshop_backend.dto.OrderDTO;
import com.example.webshop_backend.model.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/order")
class OrderController {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderController(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER') or (hasRole('ADMIN'))")
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        this.orderDAO.createOrder(orderDTO);
        return ResponseEntity.noContent().build();
    }


    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders(Authentication authentication) {
        String loggedInUsername = authentication.getName();
        System.out.println("Logged in user: " + loggedInUsername);

        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        System.out.println("Roles: " + roles);

        boolean isAdmin = roles.contains("ROLE_ADMIN");
        System.out.println("Is admin? " + isAdmin);

        List<Order> orders;

        if (isAdmin) {
            orders = (List<Order>) this.orderDAO.getAllOrders();
        } else {
            orders = this.orderDAO.findByCustomUserEmail(loggedInUsername);
        }

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Geen orders gevonden");
        }

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Long order_id,
                                          Authentication authentication) {

        Optional<Order> orderOpt = this.orderDAO.getOrderById(order_id);

        if (orderOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order niet gevonden");
        }

        Order order = orderOpt.get();
        String loggedInEmail = authentication.getName();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        String orderOwnerEmail = order.getCustomUser().getEmail();

        if (!isAdmin && !orderOwnerEmail.equals(loggedInEmail)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Je hebt geen toegang tot deze order");
        }

        return ResponseEntity.ok(order);
    }

}
