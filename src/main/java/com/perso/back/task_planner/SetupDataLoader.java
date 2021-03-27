package com.perso.back.task_planner;

import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.exception.UserConstraintViolationException;
import com.perso.back.task_planner.exception.UserNotFoundException;
import com.perso.back.task_planner.infra.dto.Privilege;
import com.perso.back.task_planner.infra.dto.Role;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.infra.repository.PrivilegeRepository;
import com.perso.back.task_planner.infra.repository.RoleRepository;
import com.perso.back.task_planner.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        UserDBDto user = null;
        try {
            user = userRepository.getByEmail("najibslassi1996@gmail.com");
        } catch (UserNotFoundException e) {
            Privilege readPrivilege
                    = createPrivilegeIfNotFound("READ_PRIVILEGE");
            Privilege writePrivilege
                    = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

            List<Privilege> adminPrivileges = Arrays.asList(
                    readPrivilege, writePrivilege);
            createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
            createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

            Role adminRole = roleRepository.getByName("ROLE_ADMIN");
            user = new UserDBDto();
            user.setFirstName("najib");
            user.setLastName("slassi");
            user.setEmail("najibslassi1996@gmail.com");
            user.setPassword(passwordEncoder.encode("test"));
            user.setRoles(Arrays.asList(adminRole));
            user.setEnabled(true);
            try {
                userRepository.saveDto(user);
            } catch (CustomMappingException customMappingException) {
                customMappingException.printStackTrace();
            } catch (UserConstraintViolationException userConstraintViolationException) {
                userConstraintViolationException.printStackTrace();
            }
        }
        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

         Privilege privilege = privilegeRepository.getByName(name);

        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {
        Role role = roleRepository.getByName(name);

        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}