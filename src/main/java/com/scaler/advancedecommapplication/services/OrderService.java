package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.OrderResponseDto;

import java.util.Optional;

public interface OrderService {
    Optional<OrderResponseDto> createOrder(String userId);
}
