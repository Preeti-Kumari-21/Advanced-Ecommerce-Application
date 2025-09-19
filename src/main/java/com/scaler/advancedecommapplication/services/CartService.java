package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.CartItemRequestDto;
import com.scaler.advancedecommapplication.dtos.ProductResponseDto;
import com.scaler.advancedecommapplication.models.CartItem;

import java.util.List;

public interface CartService {
    boolean addToCart(String userId, CartItemRequestDto cartItemRequestDto);
    boolean removeFromCart(String userId, Long productId);
    List<CartItem> fetchAllCartItems(String userId);
}
