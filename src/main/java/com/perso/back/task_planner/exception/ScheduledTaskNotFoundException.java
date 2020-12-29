package com.perso.back.task_planner.exception;

public class ScheduledTaskNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Scheduled Task not found.";
    }
}
