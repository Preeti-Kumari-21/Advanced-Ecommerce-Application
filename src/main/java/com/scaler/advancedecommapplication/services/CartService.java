package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.CartItemRequestDto;

public interface CartService {
    boolean addToCart(String userId, CartItemRequestDto cartItemRequestDto);
}
