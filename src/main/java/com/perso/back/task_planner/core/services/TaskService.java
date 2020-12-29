package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.exception.TaskConstraintViolationException;
import com.perso.back.task_planner.exception.TaskNotFoundException;
import com.perso.back.task_planner.infra.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;


    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> findAll(){
        return taskRepository.getAll();
    }


    public Task getById(Integer id) throws TaskNotFoundException {
        return taskRepository.getById(id);
    }

    @Transactional
    public Integer create(Task task) throws TaskConstraintViolationException, CustomMappingException {
        return taskRepository.save(task);
    }

    @Transactional
    public Task update(Task task) throws CustomMappingException, TaskNotFoundException {
        Task persistedTask = getById(task.getId());
        taskRepository.update(task);

        return task;
    }

    @Transactional
    public void deleteById(Integer id) throws TaskNotFoundException {
        taskRepository.delete(id);
    }
}
