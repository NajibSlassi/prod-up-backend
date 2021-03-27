package com.perso.back.task_planner.api.controller.v1;

import com.perso.back.task_planner.api.dto.TaskApiDto;
import com.perso.back.task_planner.api.mapper.TaskApiMapper;
import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.services.TaskService;
import com.perso.back.task_planner.exception.TaskConstraintViolationException;
import com.perso.back.task_planner.exception.TaskNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
class   TaskController {
    private TaskService service;
    private TaskApiMapper taskApiMapper;

    public TaskController(TaskApiMapper taskApiMapper, TaskService service) {
        this.taskApiMapper = taskApiMapper;
        this.service = service;
    }

    @GetMapping
    @CrossOrigin
    public List<TaskApiDto> findAll(@RequestParam Integer userId) {
        return taskApiMapper.mapToDtos(service.findAll(userId));
    }

    @GetMapping(value = "/{id}")
    public TaskApiDto findById(@PathVariable("id") Integer id) throws TaskNotFoundException {
        return taskApiMapper.mapToDto(service.getById(id)).orElse(null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody TaskApiDto taskApiDto) throws TaskConstraintViolationException, CustomMappingException {
        return service.create(taskApiMapper.mapToTask(taskApiDto).orElse(null));
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void update(@PathVariable( "id" ) Long id, @RequestBody TaskApiDto taskApiDto) throws Exception {
        service.update(taskApiMapper.mapToTask(taskApiDto).orElse(null));
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void delete(@PathVariable("id") Integer id) throws TaskNotFoundException {
        service.deleteById(id);
    }

    @PutMapping(value = "/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void updateOrder(@RequestParam Integer old_task_order, @RequestParam Integer new_task_order, @PathVariable("id") Integer id) throws TaskNotFoundException {
        service.updateOrder(old_task_order, new_task_order, id);
    }

}
