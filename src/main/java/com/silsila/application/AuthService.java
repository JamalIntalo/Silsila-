package com.silsila.application;

import com.silsila.dataaccess.UserDAO;
import com.silsila.domain.User;

// The logic of accounts: creating users and logging in.
public class AuthService {

    private UserDAO userDAO;

    public AuthService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // Creates an account. The password is scrambled before it is saved.
    // Returns false if the username is taken or the data is not valid.
    public boolean register(String username, String password, String role) {
        if (username == null || username.trim().equals("") || password == null || password.length() < 6) {
            return false;
        }
        if (!role.equals("CONTRIBUTOR") && !role.equals("REVIEWER")) {
            return false;
        }
        if (userDAO.findByUsername(username) != null) {
            return false;
        }
        userDAO.add(username, PasswordHasher.hash(password), role);
        return true;
    }

    // Returns the user if the username and password are right, otherwise null.
    public User login(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }
        if (PasswordHasher.matches(password, user.getPasswordHash())) {
            return user;
        }
        return null;
    }
}