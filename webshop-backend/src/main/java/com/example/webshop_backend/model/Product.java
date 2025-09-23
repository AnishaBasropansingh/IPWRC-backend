package com.example.webshop_backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue
    private long product_id;

    private String name;
    private String description;
    private double price;
    private int stock;

    @ManyToMany(mappedBy = "products")
    private List<Order> order;

    @JsonManagedReference
    @ManyToOne
    private Categorie categorie;

    public Product(String name, String description, double price, int stock, Categorie categorie){
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categorie = categorie;
    }

}
