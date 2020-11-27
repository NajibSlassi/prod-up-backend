package com.perso.back.task_planner.api.dto;

import com.perso.back.task_planner.core.model.UserRole;
import com.perso.back.task_planner.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UserApiDto {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    public String role = getUserRole().getRole().toString().toLowerCase();
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

}
