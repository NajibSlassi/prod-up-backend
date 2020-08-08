package com.perso.back.task_planner.core.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ScheduledTask {
    private Integer id;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Task task;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTask scheduledTask = (ScheduledTask) o;
        return Objects.equals(id, scheduledTask.id) &&
                Objects.equals(startDateTime, scheduledTask.startDateTime) &&
                Objects.equals(endDateTime, scheduledTask.endDateTime) &&
                Objects.equals(task, scheduledTask.task);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDateTime, endDateTime, task);
    }

    @Override
    public String toString() {
        return "TimeSpent{" +
                "id=" + id +
                ", startDateTime=" + startDateTime +
                ", endDateTime=" + endDateTime +
                ", task=" + task +
                '}';
    }
}
