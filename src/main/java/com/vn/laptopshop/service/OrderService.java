package com.vn.laptopshop.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vn.laptopshop.domain.Order;
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

    public List<Order> FetchAllOrders() {
        return this.orderRepository.findAll();
    }

    public Page<Order> FetchAllOrders(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    public Order FindOrderById(long id) {
        return this.orderRepository.findById(id).get();
    }

    public Order SaveOrder(Order order) {
        return this.orderRepository.save(order);
    }

    public void deleteOrderById(long id) {
        this.orderRepository.deleteById(id);
    }

}
