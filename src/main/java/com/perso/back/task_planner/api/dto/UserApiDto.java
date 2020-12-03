package com.perso.back.task_planner.api.dto;

import com.perso.back.task_planner.infra.dto.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class UserApiDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private boolean enabled;
    private Collection<Role> roles;
}
