package org.finalproject.bohdan.db;

import org.finalproject.bohdan.db.entity.User;

public enum Role {
    USER, ADMIN, MANAGER;

    public static Role getRole(User user) {
        int roleId = user.getRole_id();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}
