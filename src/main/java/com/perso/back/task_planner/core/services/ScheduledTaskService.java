package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.core.model.ScheduledTask;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.repository.ScheduledTaskRepository;
import com.perso.back.task_planner.infra.repository.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ScheduledTaskService {

    private final ScheduledTaskRepository scheduledTaskRepository;


    public ScheduledTaskService(ScheduledTaskRepository scheduledTaskRepository) {
        this.scheduledTaskRepository = scheduledTaskRepository;
    }


    public List<ScheduledTask> findAll(){
        return scheduledTaskRepository.getAll();
    }


    public ScheduledTask getById(Integer id) throws Exception {
        return scheduledTaskRepository.getById(id);
    }

    @Transactional
    public Integer create(ScheduledTask scheduledTask) {
        return scheduledTaskRepository.save(scheduledTask);
    }

    @Transactional
    public void update(ScheduledTask scheduledTask) {
        scheduledTaskRepository.update(scheduledTask);
    }

    @Transactional
    public void deleteById(Integer id) {
        scheduledTaskRepository.delete(id);
    }
}
