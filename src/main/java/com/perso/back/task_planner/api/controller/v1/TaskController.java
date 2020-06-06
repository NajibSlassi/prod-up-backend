package com.perso.back.task_planner.api.controller.v1;

import com.google.common.base.Preconditions;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.core.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping
    public List<Task> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Task findById(@PathVariable("id") Integer id) throws Exception {
        return RestPreconditions.checkFound(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer create(@RequestBody Task task) {
        Preconditions.checkNotNull(task);
        return service.create(task);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody Task task) throws Exception {
        Preconditions.checkNotNull(task);
        Preconditions.checkNotNull(service.getById(task.getId()));
        service.update(task);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
    }

}
