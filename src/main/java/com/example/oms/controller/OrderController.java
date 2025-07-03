package com.example.oms.controller;

import com.example.oms.entity.Order;
import com.example.oms.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService service) {
        this.orderService = service;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Order created = orderService.createOrder(order);
        return new ResponseEntity<>(created, HttpStatus.ACCEPTED);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {
        Order updated = orderService.updateOrderStatus(orderId, status);
        if (updated == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{orderId}/total")
    public ResponseEntity<Double> getOrderTotalValue(@PathVariable Long orderId) {
        Double total = orderService.getTotalValue(orderId);
        if (total == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(total);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> softDeleteOrder(@PathVariable Long orderId) {
        boolean deleted = orderService.softDeleteOrder(orderId);
        if (!deleted) {
            return ResponseEntity.notFound().build(); // Order not found or already deleted
        }
        return ResponseEntity.ok("Order marked as deleted.");
    }



}
