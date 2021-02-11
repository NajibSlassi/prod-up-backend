package com.perso.back.task_planner.api.mapper;

import com.perso.back.task_planner.api.dto.ScheduledTaskApiDto;
import com.perso.back.task_planner.core.model.ScheduledTask;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ScheduledTaskApiMapper {

    private TaskApiMapper taskApiMapper;

    public ScheduledTaskApiMapper(TaskApiMapper taskApiMapper) {
        this.taskApiMapper = taskApiMapper;
    }

    /**
     * Mappe un {@link ScheduledTaskApiDto} vers une {@link ScheduledTask} en mappant champ par champ.
     *
     * @param dto le {@link ScheduledTaskApiDto} à mapper
     * @return un {@link java.util.Optional} de {@link ScheduledTask}
     */
    public Optional<ScheduledTask> mapToScheduledTask(ScheduledTaskApiDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.setId(dto.getId());
        scheduledTask.setStartDateTime(dto.getStartDateTime());
        scheduledTask.setEndDateTime(dto.getEndDateTime());
        scheduledTask.setDescription(dto.getDescription());
        scheduledTask.setTask(taskApiMapper.mapToTask(dto.getTaskApiDto()).orElse(null));

        return Optional.of(scheduledTask);
    }

    /**
     * Mappe un {@link ScheduledTask} vers un {@link ScheduledTaskApiDto} en mappant champ par champ.
     *
     * @param scheduledTask le {@link ScheduledTask} à mapper
     * @return un {@link java.util.Optional} de {@link ScheduledTaskApiDto}
     */
    public Optional<ScheduledTaskApiDto> mapToDto(ScheduledTask scheduledTask) {

        if (scheduledTask == null) {
            return Optional.empty();
        }

        ScheduledTaskApiDto dto = new ScheduledTaskApiDto();
        dto.setId(scheduledTask.getId());
        dto.setStartDateTime(scheduledTask.getStartDateTime());
        dto.setEndDateTime(scheduledTask.getEndDateTime());
        dto.setDescription(scheduledTask.getDescription());
        dto.setTaskApiDto(taskApiMapper.mapToDto(scheduledTask.getTask()).orElse(null));


        return Optional.of(dto);
    }

    /**
     * Mappe une liste {@link List} de {@link ScheduledTask} vers une liste {@link List} de
     * {@link ScheduledTaskApiDto} en itérant sur la liste et en appelant pour chaque élément
     * la méthode {@link #mapToDto(ScheduledTask)}.
     *
     * @param scheduledTasks la liste de {@link ScheduledTask} à mapper
     * @return une liste {@link List} de {@link ScheduledTaskApiDto}
     */
    public List<ScheduledTaskApiDto> mapToDtos(List<ScheduledTask> scheduledTasks) {

        if (scheduledTasks == null) {
            return new ArrayList<>();
        }

        return scheduledTasks.stream()
                .map(scheduledTask -> mapToDto(scheduledTask).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
