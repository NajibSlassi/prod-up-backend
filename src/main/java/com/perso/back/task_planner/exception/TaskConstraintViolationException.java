package com.perso.back.task_planner.exception;

public class TaskConstraintViolationException extends Exception {

    @Override
    public String getMessage() {
        return "Task Constraint violation, maybe you are trying to add an existing element.";
    }
}
