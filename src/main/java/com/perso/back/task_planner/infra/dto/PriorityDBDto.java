package com.perso.back.task_planner.infra.dto;

import com.perso.back.task_planner.core.model.Priority;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "priority")
public class PriorityDBDto {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="name")
    private String name;
    @Column(name="value")
    private Integer value;

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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PriorityDBDto priorityDBDtoD = (PriorityDBDto) o;
        return Objects.equals(id, priorityDBDtoD.id) &&
                Objects.equals(name, priorityDBDtoD.name) &&
                Objects.equals(value, priorityDBDtoD.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }

    @Override
    public String toString() {
        return "Priority{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
