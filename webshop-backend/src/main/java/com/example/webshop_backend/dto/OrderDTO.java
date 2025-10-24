package com.example.webshop_backend.dto;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDTO {
    public LocalDateTime orderDate;
    public double totalPrice;
    public Long user_id;
    public List<Long> product_id;

}
