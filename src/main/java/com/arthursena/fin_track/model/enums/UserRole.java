package com.arthursena.fin_track.model.enums;

public enum UserRole {
    USER("USER");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
