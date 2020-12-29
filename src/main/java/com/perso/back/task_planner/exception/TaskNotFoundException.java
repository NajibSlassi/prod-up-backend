package com.perso.back.task_planner.exception;

public class TaskNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Task not found.";
    }
}
