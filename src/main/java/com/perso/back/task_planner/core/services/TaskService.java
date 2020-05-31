package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.core.model.Task;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class TaskService {
    public List<Task> findAll(){
        List list = new LinkedList<Task>();
        list.add(new Task());
        return list;
    }


    public Task getById(Integer id) {
        return new Task();
    }

    public Integer create(Task task) {
        return task.getId();
    }

    public void update(Task task) {
    }

    public void deleteById(Integer id) {
    }
}
