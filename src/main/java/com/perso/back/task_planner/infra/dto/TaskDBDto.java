
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
    @JoinColumn(name = "parent_task_id")
    private TaskDBDto parentTaskDBDto;

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

    public TaskDBDto getParentTaskDBDto() {
        return parentTaskDBDto;
    }

    public void setParentTaskDBDto(TaskDBDto parentTaskDBDto) {
        this.parentTaskDBDto = parentTaskDBDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDBDto taskDBDto1 = (TaskDBDto) o;
        return Objects.equals(id, taskDBDto1.id) &&
                Objects.equals(name, taskDBDto1.name) &&
                Objects.equals(estimatedDateTimeMin, taskDBDto1.estimatedDateTimeMin) &&
                Objects.equals(priorityDBDto, taskDBDto1.priorityDBDto) &&
                Objects.equals(stateDBDto, taskDBDto1.stateDBDto) &&
                Objects.equals(dueDate, taskDBDto1.dueDate) &&
                Objects.equals(parentTaskDBDto, taskDBDto1.parentTaskDBDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, estimatedDateTimeMin, priorityDBDto, stateDBDto, dueDate, parentTaskDBDto);
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
                ", taskDBDto=" + parentTaskDBDto +
                '}';
    }
}

