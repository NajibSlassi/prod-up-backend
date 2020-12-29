package com.perso.back.task_planner.api.controller.v1;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.ScheduledTask;
import com.perso.back.task_planner.core.services.ScheduledTaskService;
import com.perso.back.task_planner.exception.ScheduledTaskConstraintViolationException;
import com.perso.back.task_planner.exception.ScheduledTaskNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/scheduledtasks")
public class ScheduledTasksController {
    @Autowired
    private ScheduledTaskService service;

    @GetMapping
    @CrossOrigin
    public List<ScheduledTask> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ScheduledTask findById(@PathVariable("id") Integer id) throws ScheduledTaskNotFoundException {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody ScheduledTask scheduledTask) throws CustomMappingException, ScheduledTaskConstraintViolationException {
        return service.create(scheduledTask);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void update(@PathVariable( "id" ) Long id, @RequestBody ScheduledTask scheduledTask) throws Exception {
        service.update(scheduledTask);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void delete(@PathVariable("id") Integer id) throws ScheduledTaskNotFoundException {
        service.deleteById(id);
    }

}

