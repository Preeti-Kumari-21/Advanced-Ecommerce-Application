package com.scaler.advancedecommapplication.services;


import com.scaler.advancedecommapplication.models.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UserService {
     List<User> getAllUsers();
     List<User> createUser(User user);
     Optional<User> getUserById(Long id);
     Optional<User>  updateUserWithId(Long id, User user);
}
