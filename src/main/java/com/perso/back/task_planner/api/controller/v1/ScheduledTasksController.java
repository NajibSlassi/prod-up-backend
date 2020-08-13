package com.perso.back.task_planner.api.controller.v1;

import com.google.common.base.Preconditions;
import com.perso.back.task_planner.core.model.ScheduledTask;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.core.services.ScheduledTaskService;
import com.perso.back.task_planner.core.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scheduledtasks")
public class ScheduledTasksController {
    @Autowired
    private ScheduledTaskService service;

    @GetMapping
    @CrossOrigin
    public List<ScheduledTask> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public ScheduledTask findById(@PathVariable("id") Integer id) throws Exception {
        return RestPreconditions.checkFound(service.getById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody ScheduledTask scheduledTask) {
        Preconditions.checkNotNull(scheduledTask);
        return service.create(scheduledTask);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody ScheduledTask scheduledTask) throws Exception {
        Preconditions.checkNotNull(scheduledTask);
        Preconditions.checkNotNull(service.getById(scheduledTask.getId()));
        service.update(scheduledTask);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
    }

}

