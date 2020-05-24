package com.perso.back.task_planner.core.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    private Integer id;
    private String name;
    private Integer estimatedDateTimeMin;
    private Priority priority;
    private State state;
    private LocalDateTime dueDate;
    private Task parentTask;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEstimatedDateTimeMin() {
        return estimatedDateTimeMin;
    }

    public void setEstimatedDateTimeMin(Integer estimatedDateTimeMin) {
        this.estimatedDateTimeMin = estimatedDateTimeMin;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(name, task.name) &&
                Objects.equals(estimatedDateTimeMin, task.estimatedDateTimeMin) &&
                Objects.equals(priority, task.priority) &&
                Objects.equals(state, task.state) &&
                Objects.equals(dueDate, task.dueDate) &&
                Objects.equals(parentTask, task.parentTask);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, estimatedDateTimeMin, priority, state, dueDate, parentTask);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estimatedDateTimeMin=" + estimatedDateTimeMin +
                ", priority=" + priority +
                ", state=" + state +
                ", dueDate=" + dueDate +
                ", parentTask=" + parentTask +
                '}';
    }
}
