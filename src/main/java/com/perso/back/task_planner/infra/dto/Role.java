package com.perso.back.task_planner.infra.dto;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "roles")
    private Collection<UserDBDto> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    public Role() {
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<UserDBDto> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserDBDto> users) {
        this.users = users;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
                Objects.equals(name, role.name) &&
                Objects.equals(users, role.users) &&
                Objects.equals(privileges, role.privileges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users, privileges);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                ", privileges=" + privileges +
                '}';
    }
}