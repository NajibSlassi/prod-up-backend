package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.infra.dto.TaskDBDto;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TaskDBMapper {

    private StateDBMapper stateDBMapper;
    private PriorityDBMapper priorityDBMapper;
    private TaskCategoryDBMapper taskCategoryDBMapper;
    private UserDBMapper userDBMapper;

    public TaskDBMapper(StateDBMapper stateDBMapper, PriorityDBMapper priorityDBMapper,
                        TaskCategoryDBMapper taskCategoryDBMapper, UserDBMapper userDBMapper) {
        this.stateDBMapper = stateDBMapper;
        this.priorityDBMapper = priorityDBMapper;
        this.taskCategoryDBMapper = taskCategoryDBMapper;
        this.userDBMapper = userDBMapper;
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
        task.setOrder(dto.getOrder());
        task.setPriority(priorityDBMapper.mapToPriority(dto.getPriorityDBDto()).orElse(null));
        task.setState(stateDBMapper.mapToState(dto.getStateDBDto()).orElse(null));
        task.setCategory(taskCategoryDBMapper.mapToTaskCategory(dto.getCategoryDBDto())
                .orElse(null));
        task.setUser(userDBMapper.mapToUser(dto.getUserDBDto()).orElse(null));

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
        dto.setPriorityDBDto(priorityDBMapper.mapToDto(task.getPriority()).orElse(null));
        dto.setStateDBDto(stateDBMapper.mapToDto(task.getState()).orElse(null));
        dto.setOrder(task.getOrder());
        dto.setCategoryDBDto(taskCategoryDBMapper.mapToDto(task.getCategory()).orElse(null));
        dto.setUserDBDto(userDBMapper.mapToDto(task.getUser()).orElse(null));

        return Optional.of(dto);
    }

    public List<Task> mapToTasks(List<TaskDBDto> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(taskDBDto -> mapToTask(taskDBDto).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}

