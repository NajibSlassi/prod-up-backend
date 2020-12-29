package com.perso.back.task_planner.api.mapper;

import com.perso.back.task_planner.api.dto.UserApiDto;
import com.perso.back.task_planner.core.model.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserApiMapper {

    /**
     * Mappe un {@link UserApiDto} vers un {@link User} en mappant champ par champ.
     *
     * @param dto le {@link UserApiDto} à mapper
     * @return un {@link java.util.Optional} de {@link User}
     */
    public Optional<User> mapToUser(UserApiDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());

        return Optional.of(user);
    }

    /**
     * Mappe un {@link User} vers un {@link UserApiDto} en mappant champ par champ.
     *
     * @param user le {@link User} à mapper
     * @return un {@link java.util.Optional} de {@link UserApiDto}
     */
    public Optional<UserApiDto> mapToDto(User user) {

        if (user == null) {
            return Optional.empty();
        }

        UserApiDto dto = new UserApiDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());

        return Optional.of(dto);
    }
}
