package com.perso.back.task_planner.api.dto;

import com.perso.back.task_planner.core.model.Category;
import com.perso.back.task_planner.core.model.Priority;
import com.perso.back.task_planner.core.model.State;

import java.time.LocalDateTime;
import java.util.Objects;

public class TaskApiDto {
    private Integer id;
    private String name;
    private Integer estimatedDateTimeMin;
    private Priority priority;
    private State state;
    private LocalDateTime dueDate;
    private Category category;
    private UserApiDto userApiDto;

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public UserApiDto getUserApiDto() {
        return userApiDto;
    }

    public void setUserApiDto(UserApiDto userApiDto) {
        this.userApiDto = userApiDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskApiDto that = (TaskApiDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(estimatedDateTimeMin, that.estimatedDateTimeMin) &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(state, that.state) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(category, that.category) &&
                Objects.equals(userApiDto, that.userApiDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, estimatedDateTimeMin, priority, state, dueDate, category, userApiDto);
    }

    @Override
    public String toString() {
        return "TaskApiDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estimatedDateTimeMin=" + estimatedDateTimeMin +
                ", priority=" + priority +
                ", state=" + state +
                ", dueDate=" + dueDate +
                ", category=" + category +
                ", userApiDto=" + userApiDto +
                '}';
    }
}
