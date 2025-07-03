package com.example.oms.repository;

import com.example.oms.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerIdAndDeletedFalse(Long customerId);
}
