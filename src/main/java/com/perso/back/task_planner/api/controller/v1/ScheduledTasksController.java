package com.perso.back.task_planner.api.controller.v1;

import com.perso.back.task_planner.api.dto.ScheduledTaskApiDto;
import com.perso.back.task_planner.api.mapper.ScheduledTaskApiMapper;
import com.perso.back.task_planner.api.mapper.TaskApiMapper;
import com.perso.back.task_planner.core.services.TaskService;
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
    private ScheduledTaskService service;
    private ScheduledTaskApiMapper scheduledTaskApiMapper;

    public ScheduledTasksController(ScheduledTaskApiMapper scheduledTaskApiMapper,
                                    ScheduledTaskService service) {
        this.scheduledTaskApiMapper = scheduledTaskApiMapper;
        this.service = service;
    }

    @GetMapping
    @CrossOrigin
    public List<ScheduledTaskApiDto> findAll(@RequestParam Integer userId) {

        return scheduledTaskApiMapper.mapToDtos(service.findAll(userId));
    }

    @GetMapping(value = "/{id}")
    public ScheduledTaskApiDto findById(@PathVariable("id") Integer id) throws ScheduledTaskNotFoundException {
        return scheduledTaskApiMapper.mapToDto(service.getById(id)).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody ScheduledTaskApiDto scheduledTaskApiDto) throws CustomMappingException, ScheduledTaskConstraintViolationException {
        return service.create(scheduledTaskApiMapper.mapToScheduledTask(scheduledTaskApiDto).orElse(null));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void update(@PathVariable( "id" ) Long id, @RequestBody ScheduledTaskApiDto scheduledTaskApiDto) throws Exception {
        service.update(scheduledTaskApiMapper.mapToScheduledTask(scheduledTaskApiDto).orElse(null));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void delete(@PathVariable("id") Integer id) throws ScheduledTaskNotFoundException {
        service.deleteById(id);
    }

}

