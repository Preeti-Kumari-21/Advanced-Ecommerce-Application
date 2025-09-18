package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.models.User;
import com.scaler.advancedecommapplication.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("selfUserServices")
public class SelfUserServices implements UserService {

    private final UserRepository userRepository;

    public SelfUserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll() ;
    }

    @Override
    public List<User> createUser(User user) {
        List<User> users = new ArrayList<>();
        userRepository.save(user);
        users.add(user);
        return users;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> updateUserWithId(Long id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    userRepository.save(existingUser);
                    return existingUser;
                });
    }


}
