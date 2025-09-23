package com.example.webshop_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Order {
    @Id
    @GeneratedValue
    private Long order_id;
    private LocalDateTime orderDate;
    private double totalPrice;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products;

    public Order(LocalDateTime orderDate, double totalPrice, User user, List<Product> products){
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.user = user;
        this.products = products;
    }
}
