package com.scaler.advancedecommapplication.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDto {
    private Long productId;
    private Integer quantity;
}
