package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.ScheduledTask;
import com.perso.back.task_planner.exception.ScheduledTaskConstraintViolationException;
import com.perso.back.task_planner.exception.ScheduledTaskNotFoundException;
import com.perso.back.task_planner.infra.repository.ScheduledTaskRepository;
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


    public ScheduledTask getById(Integer id) throws ScheduledTaskNotFoundException {
        return scheduledTaskRepository.getById(id);
    }

    @Transactional
    public Integer create(ScheduledTask scheduledTask) throws CustomMappingException, ScheduledTaskConstraintViolationException {
        return scheduledTaskRepository.save(scheduledTask);
    }

    @Transactional
    public void update(ScheduledTask scheduledTask) throws CustomMappingException, ScheduledTaskNotFoundException {
        ScheduledTask persistedScheduledTask = getById(scheduledTask.getId());
        scheduledTaskRepository.update(scheduledTask);
    }

    @Transactional
    public void deleteById(Integer id) throws ScheduledTaskNotFoundException {
        scheduledTaskRepository.delete(id);
    }
}
