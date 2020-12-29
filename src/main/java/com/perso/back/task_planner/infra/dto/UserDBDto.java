
package com.perso.back.task_planner.infra.dto;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "user")
public class UserDBDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="email", unique=true)
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="enabled")
    private boolean enabled;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDBDto userDBDto = (UserDBDto) o;
        return enabled == userDBDto.enabled &&
                Objects.equals(id, userDBDto.id) &&
                Objects.equals(firstName, userDBDto.firstName) &&
                Objects.equals(lastName, userDBDto.lastName) &&
                Objects.equals(email, userDBDto.email) &&
                Objects.equals(password, userDBDto.password) &&
                Objects.equals(roles, userDBDto.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, enabled, roles);
    }

    @Override
    public String toString() {
        return "UserDBDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", roles=" + roles +
                '}';
    }
}
