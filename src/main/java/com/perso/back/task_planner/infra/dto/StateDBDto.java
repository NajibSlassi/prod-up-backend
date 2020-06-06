
package com.perso.back.task_planner.infra.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "state")
public class StateDBDto {
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
        StateDBDto that = (StateDBDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }

    @Override
    public String toString() {
        return "StateDBDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
