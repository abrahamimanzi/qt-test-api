package com.abrahama.qttestapi.services;

import com.abrahama.qttestapi.domain.User;
import com.abrahama.qttestapi.exceptions.QtAuthException;
import com.abrahama.qttestapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;


@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    public User ValidateUser(String email, String password) throws QtAuthException {
        if (email != null) email = email.toLowerCase();

        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String firstName, String lastName, String email, String password) throws QtAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw new QtAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new QtAuthException("Email already in use");
        Integer userId = userRepository.create(firstName, lastName, email, password);
        return userRepository.findById(userId);
    }


    @Override
    public User getUserById(Integer userId) throws QtAuthException {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public void updateUser(Integer userId, String firstName, String lastName, String email) throws QtAuthException {
        // Validate input parameters if needed

        // Call UserRepository method to update user
        userRepository.updateUser(userId, firstName, lastName, email);
    }

    @Override
    public void deleteUser(Integer userId) throws QtAuthException {
        // Validate input parameters if needed

        // Call UserRepository method to delete user
        userRepository.deleteUser(userId);
    }

    @Override
    public void userChangePassword(Integer userId, String newPassword) throws QtAuthException {
        // Validate input parameters if needed

        // Call UserRepository method to change user password
        userRepository.changeUserPassword(userId, newPassword);
    }

}
