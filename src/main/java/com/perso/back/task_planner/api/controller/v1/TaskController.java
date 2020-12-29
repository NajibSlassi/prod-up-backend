package com.perso.back.task_planner.api.controller.v1;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.core.services.TaskService;
import com.perso.back.task_planner.exception.TaskConstraintViolationException;
import com.perso.back.task_planner.exception.TaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
class   TaskController {
    @Autowired
    private TaskService service;

    @GetMapping
    @CrossOrigin
    public List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Task findById(@PathVariable("id") Integer id) throws TaskNotFoundException {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody Task task) throws TaskConstraintViolationException, CustomMappingException {
        return service.create(task);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody Task task) throws Exception {
        service.update(task);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void delete(@PathVariable("id") Integer id) throws TaskNotFoundException {
        service.deleteById(id);
    }

}
