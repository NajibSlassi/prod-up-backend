package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.State;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.dto.StateDBDto;
import com.perso.back.task_planner.infra.dto.TaskDBDto;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StateDBMapper {

    public Optional<State> mapToState(StateDBDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        State state = new State();
        state.setId(dto.getId());
        state.setName(dto.getName());
        state.setValue(dto.getValue());

        return Optional.of(state);
    }

    public Optional<StateDBDto> mapToDto(State state) {

        if (state == null) {
            return Optional.empty();
        }

        StateDBDto dto = new StateDBDto();
        dto.setId(state.getId());
        dto.setName(state.getName());
        dto.setValue(state.getValue());

        return Optional.of(dto);
    }
}
