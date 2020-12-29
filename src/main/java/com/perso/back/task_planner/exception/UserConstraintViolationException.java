package com.perso.back.task_planner.exception;

public class UserConstraintViolationException extends Exception{
    @Override
    public String getMessage() {
        return "User Constraint violation, maybe you are trying to add an existing element.";
    }
}
