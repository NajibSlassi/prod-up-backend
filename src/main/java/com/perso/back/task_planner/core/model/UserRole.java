package com.perso.back.task_planner.core.model;

import com.perso.back.task_planner.enums.Role;

public class UserRole {

    protected int id;

    public UserRole() {
    }

    public UserRole(int id, Role role) {
        this.id = id;
        this.role = role;
    }

    protected Role role;

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", role=" + role +
                '}';
    }
}