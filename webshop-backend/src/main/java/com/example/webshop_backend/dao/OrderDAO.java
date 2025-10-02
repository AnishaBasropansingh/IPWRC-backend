package com.example.webshop_backend.dao;

import com.example.webshop_backend.dto.OrderDTO;
import com.example.webshop_backend.model.CustomUser;
import com.example.webshop_backend.model.Order;
import com.example.webshop_backend.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderDAO {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public OrderDAO(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public void createOrder(OrderDTO orderDTO) {
        Optional<CustomUser> user = userRepository.findById(orderDTO.user_id);
        List<Product> products = productRepository.findAllById(orderDTO.product_id);

        if (user.isEmpty()) {
            System.out.println("User bestaat niet");
            return;
        }
        if (products.isEmpty()) {
            System.out.println("Geen producten gevonden");
            return;
        }

        Order order = new Order(orderDTO.orderDate, orderDTO.totalPrice, user.get(), products);
        orderRepository.save(order);
    }

    public Iterable<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long order_id){
        return orderRepository.findById(order_id);
    }
}

