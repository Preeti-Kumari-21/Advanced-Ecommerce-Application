package com.scaler.advancedecommapplication.controllers;

import com.scaler.advancedecommapplication.dtos.CartItemRequestDto;
import com.scaler.advancedecommapplication.services.CartService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartItemController {

    private final CartService cartService;

    public CartItemController(@Qualifier("selfCartService") CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/")
    public ResponseEntity<String> addToCart(
        @RequestHeader("X-User-Id") String userId,
        @RequestBody CartItemRequestDto cartItemRequestDto)
    {
        if(!cartService.addToCart(userId, cartItemRequestDto)){
            return ResponseEntity.badRequest().body("Something wrong with the product or user");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
