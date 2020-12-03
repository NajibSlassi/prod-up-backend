package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDBMapper {

    public Optional<User> mapToUser(UserDBDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setEnabled(dto.isEnabled());
        user.setRoles(dto.getRoles());


        return Optional.of(user);
    }

    public Optional<UserDBDto> mapToDto(User user) {

        if (user == null) {
            return Optional.empty();
        }

        UserDBDto dto = new UserDBDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setEnabled(user.isEnabled());
        dto.setRoles(user.getRoles());


        return Optional.of(dto);
    }
}
