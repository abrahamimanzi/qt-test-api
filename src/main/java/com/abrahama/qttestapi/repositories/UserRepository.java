package com.abrahama.qttestapi.repositories;

import com.abrahama.qttestapi.domain.User;
import com.abrahama.qttestapi.exceptions.QtAuthException;

import java.util.List;

public interface UserRepository {

    Integer create(String firstName, String lastName, String email, String password) throws QtAuthException;

    User findByEmailAndPassword(String email, String password) throws QtAuthException;

    Integer getCountByEmail(String email);

    List<User> getAllUsers();

    User findById(Integer userId) throws  QtAuthException;

    void updateUser(Integer userId, String firstName, String lastName, String email) throws QtAuthException;

    void deleteUser(Integer userId) throws QtAuthException;

    void changeUserPassword(Integer userId, String newPassword) throws QtAuthException;

}
