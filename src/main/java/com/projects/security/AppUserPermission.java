package com.projects.security;

public enum AppUserPermission {
    LIBRARY_READ("library:read"),
    LIBRARY_WRITE("library:write");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
