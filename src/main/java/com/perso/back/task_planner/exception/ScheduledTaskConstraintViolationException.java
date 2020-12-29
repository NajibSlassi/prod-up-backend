package com.perso.back.task_planner.exception;

public class ScheduledTaskConstraintViolationException extends Exception {

    @Override
    public String getMessage() {
        return "Scheduled task Constraint violation, maybe you are trying to add an existing element.";
    }
}