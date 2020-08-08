package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.ScheduledTask;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.dto.ScheduledTaskDBDto;
import com.perso.back.task_planner.infra.dto.TaskDBDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ScheduledTaskDBMapper {

    private StateDBMapper stateDBMapper;
    private PriorityDBMapper priorityDBMapper;
    private TaskDBMapper taskDBMapper;

    public ScheduledTaskDBMapper(StateDBMapper stateDBMapper, PriorityDBMapper priorityDBMapper, TaskDBMapper taskDBMapper) {
        this.stateDBMapper = stateDBMapper;
        this.priorityDBMapper = priorityDBMapper;
        this.taskDBMapper = taskDBMapper;
    }


    public Optional<ScheduledTask> mapToScheduledTask(ScheduledTaskDBDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.setId(dto.getId());
        scheduledTask.setTask(taskDBMapper.mapToTask(dto.getTaskDBDto()).orElse(null));
        scheduledTask.setStartDateTime(dto.getStartDateTime());
        scheduledTask.setEndDateTime(dto.getEndDateTime());

        return Optional.of(scheduledTask);
    }

    public Optional<ScheduledTaskDBDto> mapToDto(ScheduledTask scheduledTask) {

        if (scheduledTask == null) {
            return Optional.empty();
        }

        ScheduledTaskDBDto dto = new ScheduledTaskDBDto();
        dto.setId(scheduledTask.getId());
        dto.setTaskDBDto(taskDBMapper.mapToDto(scheduledTask.getTask()).orElse(null));
        dto.setStartDateTime(scheduledTask.getStartDateTime());
        dto.setEndDateTime(scheduledTask.getEndDateTime());

        return Optional.of(dto);
    }

    public List<ScheduledTask> mapToScheduledTasks(List<ScheduledTaskDBDto> dtos) {
        if (dtos == null) {
            return new ArrayList<>();
        }

        return dtos.stream()
                .map(scheduledTaskDBDto -> mapToScheduledTask(scheduledTaskDBDto).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}

