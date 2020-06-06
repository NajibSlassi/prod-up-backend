package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.infra.dto.TaskDBDto;
import com.perso.back.task_planner.infra.mapper.TaskDBMapper;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;


@Repository
public class TaskRepository {

    private static final String QUERY_FIND_SKILL_BY_ID =
            "FROM TaskDBDto as task "
                    + "where id = :id";
    private static final String QUERY_FIND_ALL_SKILLS = "FROM TaskDBDto as task ";


    @PersistenceContext
    private final EntityManager entityManager;
    private final TaskDBMapper taskDBMapper;
    private final Logger logger = LoggerFactory.getLogger(TaskRepository.class);


    public TaskRepository(EntityManager entityManager, TaskDBMapper taskDBMapper) {
        this.entityManager = entityManager;
        this.taskDBMapper = taskDBMapper;
    }


    public Task getById(Integer id) throws Exception {
        Session session = entityManager.unwrap(Session.class);
        Query<TaskDBDto> query = session.createQuery(QUERY_FIND_SKILL_BY_ID, TaskDBDto.class);
        query.setParameter("id", id);

        TaskDBDto taskDBDto = query.getSingleResult();

        Optional<Task> optionalTask = taskDBMapper.mapToTask(taskDBDto);
        session.evict(taskDBDto);
        return optionalTask.get();
    }

    public List<Task> getAll() {
        Session session = entityManager.unwrap(Session.class);
        Query<TaskDBDto> query = session.createQuery(QUERY_FIND_ALL_SKILLS, TaskDBDto.class);

        return taskDBMapper.mapToTasks(query.getResultList());
    }

    public Integer save(Task task) {

        Session session = entityManager.unwrap(Session.class);
        Optional<TaskDBDto> optionalTaskToCreate = taskDBMapper.mapToDto(task);

        if (optionalTaskToCreate.isPresent()) {
            TaskDBDto taskToCreate = optionalTaskToCreate.get();
            session.save(taskToCreate);

            return taskDBMapper.mapToTask(taskToCreate).get().getId();
        } else {
            logger.debug("An optional task cannot be mapped : {}", optionalTaskToCreate);
            //throw new CustomMappingException();
            return 0;
        }
    }


}
