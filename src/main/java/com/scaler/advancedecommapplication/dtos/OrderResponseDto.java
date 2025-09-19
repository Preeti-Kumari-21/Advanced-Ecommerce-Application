package com.scaler.advancedecommapplication.dtos;

import com.scaler.advancedecommapplication.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {
    private Long id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private LocalDateTime createdDate;
}
