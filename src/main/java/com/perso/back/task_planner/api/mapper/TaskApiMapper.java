package com.perso.back.task_planner.api.mapper;

import com.perso.back.task_planner.api.dto.TaskApiDto;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.mapper.UserDBMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskApiMapper {

    private UserApiMapper userApiMapper;

    public TaskApiMapper(UserApiMapper userApiMapper) {
        this.userApiMapper = userApiMapper;
    }

    /**
     * Mappe un {@link TaskApiDto} vers une {@link Task} en mappant champ par champ.
     *
     * @param dto le {@link TaskApiDto} à mapper
     * @return un {@link java.util.Optional} de {@link Task}
     */
    public Optional<Task> mapToTask(TaskApiDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setEstimatedDateTimeMin(dto.getEstimatedDateTimeMin());
        task.setPriority(dto.getPriority());
        task.setState(dto.getState());
        task.setOrder(dto.getOrder());
        task.setDueDate(dto.getDueDate());
        task.setCategory(dto.getCategory());
        task.setUser(userApiMapper.mapToUser(dto.getUserApiDto()).orElse(null));

        return Optional.of(task);
    }

    /**
     * Mappe un {@link Task} vers un {@link TaskApiDto} en mappant champ par champ.
     *
     * @param task le {@link Task} à mapper
     * @return un {@link java.util.Optional} de {@link TaskApiDto}
     */
    public Optional<TaskApiDto> mapToDto(Task task) {

        if (task == null) {
            return Optional.empty();
        }

        TaskApiDto dto = new TaskApiDto();
        dto.setId(task.getId());
        dto.setName(task.getName());
        dto.setEstimatedDateTimeMin(task.getEstimatedDateTimeMin());
        dto.setPriority(task.getPriority());
        dto.setState(task.getState());
        dto.setOrder(task.getOrder());
        dto.setDueDate(task.getDueDate());
        dto.setCategory(task.getCategory());
        dto.setUserApiDto(userApiMapper.mapToDto(task.getUser()).orElse(null));

        return Optional.of(dto);
    }

    /**
     * Mappe une liste {@link List} de {@link Task} vers une liste {@link List} de {@link TaskApiDto}
     * en itérant sur la liste et en appelant pour chaque élément la méthode {@link #mapToDto(Task)}.
     *
     * @param tasks la liste de {@link Task} à mapper
     * @return une liste {@link List} de {@link TaskApiDto}
     */
    public List<TaskApiDto> mapToDtos(List<Task> tasks) {

        if (tasks == null) {
            return new ArrayList<>();
        }

        return tasks.stream()
                .map(task -> mapToDto(task).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

}
