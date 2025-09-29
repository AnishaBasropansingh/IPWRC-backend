package com.example.webshop_backend.dto;

import com.example.webshop_backend.model.Product;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    public LocalDateTime orderDate;
    public double totalPrice;
    public String user;
    public List<Product> products;
}
