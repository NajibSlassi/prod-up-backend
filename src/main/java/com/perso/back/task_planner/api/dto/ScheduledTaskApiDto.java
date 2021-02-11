package com.perso.back.task_planner.api.dto;

import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.dto.TaskDBDto;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduledTaskApiDto {
    private Integer id;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private TaskApiDto taskApiDto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public TaskApiDto getTaskApiDto() {
        return taskApiDto;
    }

    public void setTaskApiDto(TaskApiDto taskApiDto) {
        this.taskApiDto = taskApiDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTaskApiDto that = (ScheduledTaskApiDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(taskApiDto, that.taskApiDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, startDateTime, endDateTime, taskApiDto);
    }

    @Override
    public String toString() {
        return "ScheduledTaskApiDto{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", taskApiDto=" + taskApiDto +
                '}';
    }
}
