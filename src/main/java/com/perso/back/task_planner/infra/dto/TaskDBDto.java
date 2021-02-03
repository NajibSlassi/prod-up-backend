
package com.perso.back.task_planner.infra.dto;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "task")
public class TaskDBDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="name", unique = true)
    private String name;
    @Column(name="estimated_time_min")
    private Integer estimatedDateTimeMin;
    @OneToOne
    @JoinColumn(name = "priority_id")
    private PriorityDBDto priorityDBDto;
    @OneToOne
    @JoinColumn(name = "state_id")
    private StateDBDto stateDBDto;
    @Column(name="due_date")
    private LocalDateTime dueDate;
    @OneToOne
    @JoinColumn(name = "category_id")
    private TaskCategoryDBDto categoryDBDto ;

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

    public PriorityDBDto getPriorityDBDto() {
        return priorityDBDto;
    }

    public void setPriorityDBDto(PriorityDBDto priorityDBDto) {
        this.priorityDBDto = priorityDBDto;
    }

    public StateDBDto getStateDBDto() {
        return stateDBDto;
    }

    public void setStateDBDto(StateDBDto stateDBDto) {
        this.stateDBDto = stateDBDto;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public TaskCategoryDBDto getCategoryDBDto() {
        return categoryDBDto;
    }

    public void setCategoryDBDto(TaskCategoryDBDto categoryDBDto) {
        this.categoryDBDto = categoryDBDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDBDto taskDBDto = (TaskDBDto) o;
        return Objects.equals(id, taskDBDto.id) &&
                Objects.equals(name, taskDBDto.name) &&
                Objects.equals(estimatedDateTimeMin, taskDBDto.estimatedDateTimeMin) &&
                Objects.equals(priorityDBDto, taskDBDto.priorityDBDto) &&
                Objects.equals(stateDBDto, taskDBDto.stateDBDto) &&
                Objects.equals(dueDate, taskDBDto.dueDate) &&
                Objects.equals(categoryDBDto, taskDBDto.categoryDBDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, estimatedDateTimeMin, priorityDBDto, stateDBDto, dueDate, categoryDBDto);
    }

    @Override
    public String toString() {
        return "TaskDBDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", estimatedDateTimeMin=" + estimatedDateTimeMin +
                ", priorityDBDto=" + priorityDBDto +
                ", stateDBDto=" + stateDBDto +
                ", dueDate=" + dueDate +
                ", categoryDBDto=" + categoryDBDto +
                '}';
    }
}

