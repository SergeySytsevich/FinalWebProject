
package com.epam.webproject.entity;


public enum UserRole {
     USER("user"), ADMIN("admin"), CLIENT("client");

    UserRole(String role) {
        this.role = role;
    }

    String role;

    public String getRole() {
        return this.role;
    }

    public static UserRole findByName(String name) throws EnumConstantNotPresentException {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.getRole().equals(name)) {
                return userRole;
            }
        }
        throw new EnumConstantNotPresentException(UserRole.class, "Error with searching user role");
    }
}
