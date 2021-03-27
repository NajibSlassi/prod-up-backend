package com.perso.back.task_planner.aop.security;

import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.exception.UserNotFoundException;
import com.perso.back.task_planner.infra.dto.Privilege;
import com.perso.back.task_planner.infra.dto.Role;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.infra.repository.RoleRepository;
import com.perso.back.task_planner.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetailsExtended loadUserByUsername(String email)
            throws UsernameNotFoundException {

        UserDBDto user = null;
        try {
            user = userRepository.getByEmail(email);
        } catch (UserNotFoundException e) {
            return new UserDetailsExtended(
                    0, " ", " ", true, true, true, true,
                    getAuthorities(Arrays.asList(
                            roleRepository.getByName("ROLE_USER"))));
        }

        return new UserDetailsExtended(
               user.getId(), user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

}
