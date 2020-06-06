package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.Priority;
import com.perso.back.task_planner.core.model.State;
import com.perso.back.task_planner.infra.dto.PriorityDBDto;
import com.perso.back.task_planner.infra.dto.StateDBDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PriorityDBMapper {
    public Optional<Priority> mapToPriority(PriorityDBDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        Priority priority = new Priority();
        priority.setId(dto.getId());
        priority.setName(dto.getName());
        priority.setValue(dto.getValue());

        return Optional.of(priority);
    }

    public Optional<PriorityDBDto> mapToDto(Priority priority) {

        if (priority == null) {
            return Optional.empty();
        }

        PriorityDBDto dto = new PriorityDBDto();
        dto.setId(priority.getId());
        dto.setName(priority.getName());
        dto.setValue(priority.getValue());

        return Optional.of(dto);
    }
}
