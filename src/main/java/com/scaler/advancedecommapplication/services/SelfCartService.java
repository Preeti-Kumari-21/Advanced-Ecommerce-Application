package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.CartItemRequestDto;
import com.scaler.advancedecommapplication.models.CartItem;
import com.scaler.advancedecommapplication.models.Product;
import com.scaler.advancedecommapplication.models.User;
import com.scaler.advancedecommapplication.repositories.CartItemRepository;
import com.scaler.advancedecommapplication.repositories.ProductRepository;
import com.scaler.advancedecommapplication.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service("selfCartService")
public class SelfCartService implements CartService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public SelfCartService(ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean addToCart(String userId, CartItemRequestDto cartItemRequestDto) {
        //Look for product
        Optional<Product> productOptional = productRepository.findById(cartItemRequestDto.getProductId());
        if (productOptional.isEmpty()) {
            return false;
        }
        Product product = productOptional.get();

        //Check stock
        if (product.getStockQuantity() < cartItemRequestDto.getQuantity()) {
            return false;
        }

        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if (userOptional.isEmpty()) {
            return false;
        }
        //Create CartItem and save

        User user = userOptional.get();

        CartItem existingCartItem = cartItemRepository.findByUserAndProduct(user, product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequestDto.getQuantity());
            existingCartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(existingCartItem.getQuantity())));
            cartItemRepository.save(existingCartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequestDto.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequestDto.getQuantity())));
            cartItemRepository.save(cartItem);
        }
        return true;
    }
}
