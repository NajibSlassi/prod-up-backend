
package com.perso.back.task_planner.infra.dto;

import com.perso.back.task_planner.core.model.Task;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "scheduled_task")
public class ScheduledTaskDBDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="start_date_time")
    private LocalDateTime startDateTime;
    @Column(name="end_date_time")
    private LocalDateTime endDateTime;
    @OneToOne
    @JoinColumn(name = "task_id")
    private TaskDBDto taskDBDto;

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

    public TaskDBDto getTaskDBDto() {
        return taskDBDto;
    }

    public void setTaskDBDto(TaskDBDto taskDBDto) {
        this.taskDBDto = taskDBDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduledTaskDBDto that = (ScheduledTaskDBDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(startDateTime, that.startDateTime) &&
                Objects.equals(endDateTime, that.endDateTime) &&
                Objects.equals(taskDBDto, that.taskDBDto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDateTime, endDateTime, taskDBDto);
    }
}

