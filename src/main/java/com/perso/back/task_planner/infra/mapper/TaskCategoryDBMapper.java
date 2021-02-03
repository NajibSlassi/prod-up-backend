package com.perso.back.task_planner.infra.mapper;

import com.perso.back.task_planner.core.model.Category;
import com.perso.back.task_planner.infra.dto.TaskCategoryDBDto;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class TaskCategoryDBMapper {

    public Optional<Category> mapToTaskCategory(TaskCategoryDBDto dto) {

        if (dto == null) {
            return Optional.empty();
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());

        return Optional.of(category);
    }

    public Optional<TaskCategoryDBDto> mapToDto(Category category) {

        if (category == null) {
            return Optional.empty();
        }

        TaskCategoryDBDto dto = new TaskCategoryDBDto();
        dto.setId(category.getId());
        dto.setName(category.getName());

        return Optional.of(dto);
    }
}
