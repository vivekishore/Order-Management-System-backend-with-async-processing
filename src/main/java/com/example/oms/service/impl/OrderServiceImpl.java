package com.example.oms.service.impl;

import com.example.oms.entity.Order;
import com.example.oms.entity.OrderItem;
import com.example.oms.repository.OrderRepository;
import com.example.oms.service.OrderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private static final Set<String> STATUSES_ALLOWING_TOTAL = Set.of("Confirmed", "Shipped", "Delivered");


    public OrderServiceImpl(OrderRepository repo) {
        this.orderRepository = repo;
    }

    @Override
    @Transactional
    public Order createOrder(Order order) {
        // Set status to "Pending" initially
        order.setStatus("Pending");
        order.setTotalValue(0.0);
        order.setDeleted(false);

        // Save order (cascades items)
        Order savedOrder = orderRepository.save(order);

        // Trigger async finalization
        finalizeOrderAsync(savedOrder.getId());

        return savedOrder;
    }

    @Async
    public void finalizeOrderAsync(Long orderId) {
        // Fetch order fresh from DB
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null || !"Pending".equals(order.getStatus())) {
            return; // no work needed
        }

        // Calculate totalValue
        double total = order.getItems()
                .stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        order.setTotalValue(total);
        order.setStatus("Confirmed");

        // Save changes
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerIdAndDeletedFalse(customerId);
    }

    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null) {
            return null;
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public Double getTotalValue(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order == null || !STATUSES_ALLOWING_TOTAL.contains(order.getStatus())) {
            return null;
        }
        return order.getTotalValue();
    }

    @Override
    public boolean softDeleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null && !order.getDeleted()) {
            order.setDeleted(true);
            orderRepository.save(order);
            return true;
        }
        return false;
    }

}
