
package com.perso.back.task_planner.infra.dto;

import com.perso.back.task_planner.core.model.UserRole;
import com.perso.back.task_planner.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user")
public class UserDBDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="firstname")
    private String firstName;
    @Column(name="lastname")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="role")
    public String role = getUserRole().getRole().toString().toLowerCase();
    @Transient
    private List<GrantedAuthority> authorities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<GrantedAuthority> getAuthorities() {
        UserRole role = this.getUserRole();
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(role.getRole().authority()));
        return authorities;
    }

    public void setAuthorities(List<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public UserRole getUserRole() {
        return new UserRole(1, Role.ADMINISTRATOR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDBDto userDBDto = (UserDBDto) o;
        return Objects.equals(id, userDBDto.id) &&
                Objects.equals(username, userDBDto.username) &&
                Objects.equals(password, userDBDto.password) &&
                Objects.equals(firstName, userDBDto.firstName) &&
                Objects.equals(lastName, userDBDto.lastName) &&
                Objects.equals(email, userDBDto.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, firstName, lastName, email);
    }

    @Override
    public String toString() {
        return "UserDBDto{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
