package com.perso.back.task_planner.api.controller.v1.exception;

import com.perso.back.task_planner.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(value = TaskNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(TaskNotFoundException e) {

        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.NOT_FOUND.value()));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = ScheduledTaskNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(ScheduledTaskNotFoundException e) {

        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.NOT_FOUND.value()));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericNotFoundException(UserNotFoundException e) {

        CustomErrorResponse error = new CustomErrorResponse("NOT_FOUND_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.NOT_FOUND.value()));

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = TaskConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericConflictException(TaskConstraintViolationException e) {

        CustomErrorResponse error = new CustomErrorResponse("CONFLICT_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.CONFLICT.value()));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = ScheduledTaskConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericConflictException(ScheduledTaskConstraintViolationException e) {

        CustomErrorResponse error = new CustomErrorResponse("CONFLICT_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.CONFLICT.value()));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = UserConstraintViolationException.class)
    public ResponseEntity<CustomErrorResponse> handleGenericConflictException(UserConstraintViolationException e) {

        CustomErrorResponse error = new CustomErrorResponse("CONFLICT_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.CONFLICT.value()));

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(value = CustomMappingException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodCustomMappingException(CustomMappingException e) {

        CustomErrorResponse error = new CustomErrorResponse("MAPPING_ERROR", e.getMessage());

        error.setTimestamp(LocalDateTime.now());

        error.setStatus((HttpStatus.BAD_REQUEST.value()));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }
}