package com.myapp.backend.model;

public abstract class User {
    private String name;
    private String id;
    private String email;
    private String password;
    private boolean isLoggedIn;

    public User(String name, String id, String email, String password) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.isLoggedIn = false;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }
    
    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean login(String password) {
        if (this.password.equals(password)) {
            this.isLoggedIn = true;
            return true;
        }
        return false;
    }
    
    public void logout() {
        this.isLoggedIn = false;
    }

    public abstract void displayUserInfo();
}