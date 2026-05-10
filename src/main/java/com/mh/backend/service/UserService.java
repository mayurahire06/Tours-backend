package com.mh.backend.service;

import com.mh.backend.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    // Create a new user
    User createUser(User user);

    //login user
    User loginUser(String email, String password);

    // Get all users
    List<User> getAllUsers();

    // Get user by ID
    Optional<User> getUserById(Long id);

    // Get user by email
    Optional<User> getUserByEmail(String email);

    // Update user
    User updateUser(Long id, User updatedUser);

    // Delete user
    void deleteUser(Long id);

}
