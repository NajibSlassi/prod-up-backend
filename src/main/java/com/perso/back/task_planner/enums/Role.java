package com.perso.back.task_planner.enums;

public enum Role {
    ADMINISTRATOR, CLIENT;

    public String authority() {
        return this.name().toLowerCase();
    }
}