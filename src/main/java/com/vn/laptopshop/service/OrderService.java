package com.vn.laptopshop.service;

import org.springframework.stereotype.Service;

import com.vn.laptopshop.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public long CountOrders() {
        return this.orderRepository.count();
    }

}
