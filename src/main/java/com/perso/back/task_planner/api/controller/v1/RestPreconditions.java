package com.perso.back.task_planner.api.controller.v1;

public class RestPreconditions {
    public static <T> T checkFound(T resource) {
        if (resource == null) {
            throw new RuntimeException();
        }
        return resource;
    }
}