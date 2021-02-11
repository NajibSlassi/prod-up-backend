package com.perso.back.task_planner.infra.dto;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "category")
public class CategoryDBDto {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name="name")
    private String name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryDBDto that = (CategoryDBDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "TaskCategoryDBDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
