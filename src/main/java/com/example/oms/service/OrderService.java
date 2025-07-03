package com.example.oms.service;

import com.example.oms.entity.Order;
import com.example.oms.entity.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);
    List<Order> getOrdersByCustomerId(Long customerId);
    Order updateOrderStatus(Long orderId, String status);
    Double getTotalValue(Long orderId);
    boolean softDeleteOrder(Long orderId);
}
