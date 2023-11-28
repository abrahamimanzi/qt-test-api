package com.abrahama.qttestapi.services;

import com.abrahama.qttestapi.domain.User;
import com.abrahama.qttestapi.exceptions.QtAuthException;

import java.util.List;

public interface UserService {
    User ValidateUser(String email, String password) throws QtAuthException;

    User registerUser(String firstName, String lastName, String email, String password) throws QtAuthException;

    User getUserById(Integer userId) throws QtAuthException;

    List<User> getAllUsers() throws QtAuthException;

    void updateUser(Integer userId, String firstName, String lastName, String email) throws QtAuthException;

    void deleteUser(Integer userId) throws QtAuthException;

    void userChangePassword(Integer userId, String newPassword) throws QtAuthException;
}
