package com.silsila.dataaccess;

import com.silsila.domain.User;

// Finds and saves users in the database.
public class UserDAO {

    private Database database;

    public UserDAO(Database database) {
        this.database = database;
    }

    // Returns the user with this username, or null if there is none.
    public User findByUsername(String username) {
        for (User u : database.users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    // Saves a new user. The id is the next free number.
    public User add(String username, String passwordHash, String role) {
        User user = new User(database.users.size() + 1, username, passwordHash, role);
        database.users.add(user);
        return user;
    }
}