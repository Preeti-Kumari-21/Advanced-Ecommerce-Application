package com.scaler.advancedecommapplication.controllers;

import com.scaler.advancedecommapplication.dtos.OrderResponseDto;
import com.scaler.advancedecommapplication.repositories.OrderRepository;
import com.scaler.advancedecommapplication.services.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(@Qualifier("selfOrderService") OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/")
    public ResponseEntity<Optional<OrderResponseDto>> createOrder(
            @RequestHeader("X-User-ID") String userId) {
        return new ResponseEntity<>(orderService.createOrder(userId), HttpStatus.CREATED);
    }

}
