package com.perso.back.task_planner.exception;

public class UserNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "User not found.";
    }
}
