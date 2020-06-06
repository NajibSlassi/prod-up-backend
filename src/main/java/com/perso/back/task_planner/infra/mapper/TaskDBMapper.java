package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.dto.TaskDBDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskDBMapper {

    private StateDBMapper stateDBMapper;
    private PriorityDBMapper priorityDBMapper;

    public TaskDBMapper(StateDBMapper stateDBMapper, PriorityDBMapper priorityDBMapper) {
        this.stateDBMapper = stateDBMapper;
        this.priorityDBMapper = priorityDBMapper;
    }


    public Optional<Task> mapToTask(TaskDBDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDueDate(dto.getDueDate());
        task.setEstimatedDateTimeMin(dto.getEstimatedDateTimeMin());
        task.setPriority(priorityDBMapper.mapToPriority(dto.getPriorityDBDto()).get());
        task.setState(stateDBMapper.mapToState(dto.getStateDBDto()).get());
        task.setParentTask(mapToTask(dto.getParentTaskDBDto()).orElse(null));

        return Optional.of(task);
    }

    public Optional<TaskDBDto> mapToDto(Task task) {

        if (task == null) {
            return Optional.empty();
        }

        TaskDBDto dto = new TaskDBDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setDueDate(task.getDueDate());
        dto.setEstimatedDateTimeMin(task.getEstimatedDateTimeMin());
        dto.setPriorityDBDto(priorityDBMapper.mapToDto(task.getPriority()).get());
        dto.setStateDBDto(stateDBMapper.mapToDto(task.getState()).get());
        dto.setParentTaskDBDto(mapToDto(task.getParentTask()).get());

        return Optional.of(dto);
    }
}

