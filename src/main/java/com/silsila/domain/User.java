package com.silsila.domain;

// A person who has an account on the website.
// The role decides what the person may do:
//   "CONTRIBUTOR" can propose a new link between an issue and its evidence.
//   "REVIEWER"    can do that too, and can approve or reject proposed links.
// Students who only read the website do not need an account.
public class User {

    private int id;
    private String username;
    private String passwordHash;   // NEVER the real password, only a scrambled version of it
    private String role;

    public User(int id, String username, String passwordHash, String role) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRole() {
        return role;
    }

    public boolean isReviewer() {
        return role.equals("REVIEWER");
    }
}