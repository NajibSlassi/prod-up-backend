package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.LinkedList;
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


    public Task getById(Integer id) throws Exception {
        return taskRepository.getById(id);
    }

    @Transactional
    public Integer create(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public void update(Task task) {
        taskRepository.update(task);
    }

    @Transactional
    public void deleteById(Integer id) {
        taskRepository.delete(id);
    }
}
