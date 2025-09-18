package com.scaler.advancedecommapplication.services;


import com.scaler.advancedecommapplication.dtos.UserRequestDto;
import com.scaler.advancedecommapplication.dtos.UserResponseDto;
import com.scaler.advancedecommapplication.models.User;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

public interface UserService {
     List<UserResponseDto> getAllUsers();
     String createUser(UserRequestDto userRequestDto);
     Optional<UserResponseDto> getUserById(Long id);
     Optional<UserResponseDto> updateUserWithId(Long id, UserRequestDto userRequestDto);
}
