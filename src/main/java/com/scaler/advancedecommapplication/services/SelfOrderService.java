package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.OrderItemDto;
import com.scaler.advancedecommapplication.dtos.OrderResponseDto;
import com.scaler.advancedecommapplication.dtos.UserResponseDto;
import com.scaler.advancedecommapplication.models.*;
import com.scaler.advancedecommapplication.repositories.CartItemRepository;
import com.scaler.advancedecommapplication.repositories.OrderRepository;
import com.scaler.advancedecommapplication.repositories.ProductRepository;
import com.scaler.advancedecommapplication.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("selfOrderService")
public class SelfOrderService implements OrderService {
    private final UserRepository userRepository;
    private final CartService cartService;
    private final OrderRepository orderRepository;

    public SelfOrderService(UserRepository userRepository, CartService cartService, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }


    @Transactional
    @Override
    public Optional<OrderResponseDto> createOrder(String userId) {

        //Validate for cart
        List<CartItem> cartItems = cartService.fetchAllCartItems(userId);
        if(cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        //Validate for user
        Optional<User> userOptional = userRepository.findById(Long.valueOf(userId));
        if(userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = userOptional.get();

        //Calculate total amount
        BigDecimal totalAmount = cartItems.stream()
                .map(CartItem :: getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal :: add);
        //Create order and order items and save to DB
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                        null,
                        item.getProduct(),
                        item.getQuantity(),
                        item.getPrice(),
                        order
                )).toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        //Clear cart
        cartService.clearCart(userId);

        return Optional.of(toOrderResponseDto(savedOrder));
    }

    // place this inside your service class
    private OrderResponseDto toOrderResponseDto(Order order) {
        if (order == null) return null;

        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(order.getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setCreatedDate(order.getCreationDate());

        List<OrderItemDto> items = Optional.ofNullable(order.getItems())
                .orElseGet(Collections::emptyList)
                .stream()
                .map(item -> {
                    OrderItemDto it = new OrderItemDto();
                    it.setId(item.getId());
                    it.setQuantity(item.getQuantity());
                    it.setPrice(item.getPrice());
                    it.setSubTotal(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                    return it;
                })
                .collect(Collectors.toList());

        dto.setItems(items);
        return dto;
    }

}
