package com.scaler.advancedecommapplication.services;

import com.scaler.advancedecommapplication.dtos.AddressDto;
import com.scaler.advancedecommapplication.dtos.UserRequestDto;
import com.scaler.advancedecommapplication.dtos.UserResponseDto;
import com.scaler.advancedecommapplication.models.Address;
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
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponseDto> userResponseDtos = new ArrayList<>();
        for (User user : users) {
            UserResponseDto dto = convertUserToUserResponseDto(user);
            userResponseDtos.add(dto);
        }
        return userResponseDtos;
    }

    @Override
    public String createUser(UserRequestDto userRequestDto) {
        User user = convertUserRequestDtoToUser(userRequestDto);
        userRepository.save(user);
        return "User created successfully with ID: " + user.getId();
    }

    @Override
    public Optional<UserResponseDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::convertUserToUserResponseDto);
    }

    @Override
    public Optional<UserResponseDto> updateUserWithId(Long id, UserRequestDto userRequestDto) {

        return userRepository.findById(id)
                .map(existingUser -> {
                    applyUserRequestDtoToExistingUser(userRequestDto, existingUser);
                    User savedUser = userRepository.save(existingUser);
                    return convertUserToUserResponseDto(savedUser);
                });
    }

    private void applyUserRequestDtoToExistingUser(UserRequestDto dto, User existingUser) {
        if (dto.getFirstName() != null) existingUser.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) existingUser.setLastName(dto.getLastName());
        if (dto.getEmail() != null) existingUser.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) existingUser.setPhoneNumber(dto.getPhoneNumber());

        // Address copying — handle nulls carefully
        if (dto.getAddress() != null) {
            if (existingUser.getAddress() == null) {
                existingUser.setAddress(new Address());
            }
            AddressDto ad = dto.getAddress();
            Address a = existingUser.getAddress();
            if (ad.getStreet() != null) a.setStreet(ad.getStreet());
            if (ad.getCity() != null) a.setCity(ad.getCity());
            if (ad.getState() != null) a.setState(ad.getState());
            if (ad.getZipCode() != null) a.setZipCode(ad.getZipCode());
            if (ad.getCountry() != null) a.setCountry(ad.getCountry());
        }
    }

    private UserResponseDto convertUserToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId().toString());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setRole(user.getRole());
        if (user.getAddress() != null) {
            AddressDto addressDto = new AddressDto();
            addressDto.setStreet(user.getAddress().getStreet());
            addressDto.setCity(user.getAddress().getCity());
            addressDto.setState(user.getAddress().getState());
            addressDto.setZipCode(user.getAddress().getZipCode());
            addressDto.setCountry(user.getAddress().getCountry());
            dto.setAddress(addressDto);
        }
        return dto;
    }

    private User convertUserRequestDtoToUser(UserRequestDto userRequestDto) {
        User user = new User();
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        if (userRequestDto.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequestDto.getAddress().getStreet());
            address.setCity(userRequestDto.getAddress().getCity());
            address.setState(userRequestDto.getAddress().getState());
            address.setZipCode(userRequestDto.getAddress().getZipCode());
            address.setCountry(userRequestDto.getAddress().getCountry());
            user.setAddress(address);
        }
        return user;
    }




}
