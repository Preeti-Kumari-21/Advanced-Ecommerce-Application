package com.scaler.advancedecommapplication.repositories;

import com.scaler.advancedecommapplication.models.CartItem;
import com.scaler.advancedecommapplication.models.Product;
import com.scaler.advancedecommapplication.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    CartItem findByUserAndProduct(User user, Product product);
}
