package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.ScheduledTask;
import com.perso.back.task_planner.exception.ScheduledTaskNotFoundException;
import com.perso.back.task_planner.exception.ScheduledTaskConstraintViolationException;
import com.perso.back.task_planner.infra.dto.ScheduledTaskDBDto;
import com.perso.back.task_planner.infra.mapper.ScheduledTaskDBMapper;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Repository
public class ScheduledTaskRepository {

    private static final String QUERY_FIND_SCHEDULED_TASK_BY_ID =
            "FROM ScheduledTaskDBDto as ScheduledTask "
                    + "where id = :id";
    private static final String QUERY_FIND_ALL_SCHEDULED_TASKS = "FROM ScheduledTaskDBDto as task "
            + "where task.taskDBDto.userDBDto.id = :userId";

    @PersistenceContext
    private final EntityManager entityManager;
    private final ScheduledTaskDBMapper scheduledTaskDBMapper;
    private final Logger logger = LoggerFactory.getLogger(ScheduledTaskRepository.class);


    public ScheduledTaskRepository(EntityManager entityManager, ScheduledTaskDBMapper scheduledTaskDBMapper) {
        this.entityManager = entityManager;
        this.scheduledTaskDBMapper = scheduledTaskDBMapper;
    }


    public ScheduledTask getById(Integer id) throws ScheduledTaskNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        Query<ScheduledTaskDBDto> query = session.createQuery(QUERY_FIND_SCHEDULED_TASK_BY_ID, ScheduledTaskDBDto.class);
        query.setParameter("id", id);

        ScheduledTaskDBDto scheduledTaskDBDto = null;
        try {
            scheduledTaskDBDto = query.getSingleResult();
        } catch (NoResultException e) {
            throw new ScheduledTaskNotFoundException();
        }

        Optional<ScheduledTask> optionalScheduledTask = scheduledTaskDBMapper.mapToScheduledTask(scheduledTaskDBDto);
        session.evict(scheduledTaskDBDto);
        return optionalScheduledTask.get();
    }

    public List<ScheduledTask> getAll(Integer userId) {
        Session session = entityManager.unwrap(Session.class);
        Query<ScheduledTaskDBDto> query = session.createQuery(QUERY_FIND_ALL_SCHEDULED_TASKS, ScheduledTaskDBDto.class);
        query.setParameter("userId", userId);

        return scheduledTaskDBMapper.mapToScheduledTasks(query.getResultList());
    }

    public Integer save(ScheduledTask scheduledTask) throws CustomMappingException, ScheduledTaskConstraintViolationException {

        Session session = entityManager.unwrap(Session.class);
        Optional<ScheduledTaskDBDto> optionalScheduledTaskToCreate = scheduledTaskDBMapper.mapToDto(scheduledTask);

        if (optionalScheduledTaskToCreate.isPresent()) {
            ScheduledTaskDBDto scheduledTaskToCreate = optionalScheduledTaskToCreate.get();
            try {
                session.save(scheduledTaskToCreate);
            } catch (ConstraintViolationException e) {
                session.clear();
                throw new ScheduledTaskConstraintViolationException();
            }

            return scheduledTaskDBMapper.mapToScheduledTask(scheduledTaskToCreate).get().getId();
        } else {
            logger.debug("An optional scheduled task cannot be mapped : {}", optionalScheduledTaskToCreate);
            throw new CustomMappingException();
        }
    }


    public void update(ScheduledTask scheduledTask) throws CustomMappingException {
        Session session = entityManager.unwrap(Session.class);
        Optional<ScheduledTaskDBDto> optionalScheduledTaskToUpdate = scheduledTaskDBMapper.mapToDto(scheduledTask);

        if (optionalScheduledTaskToUpdate.isPresent()) {
            ScheduledTaskDBDto scheduledTaskToUpdate = optionalScheduledTaskToUpdate.get();
            session.update(scheduledTaskToUpdate);
        } else {
            logger.debug("An optional task cannot be mapped : {}", optionalScheduledTaskToUpdate);
            throw new CustomMappingException();
        }
    }

    public void delete(Integer id) throws ScheduledTaskNotFoundException {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<ScheduledTaskDBDto> queryGet = session.createQuery(QUERY_FIND_SCHEDULED_TASK_BY_ID, ScheduledTaskDBDto.class);
            queryGet.setParameter("id", id);
            ScheduledTaskDBDto scheduledTaskDBDto = queryGet.getSingleResult();
            session.delete(scheduledTaskDBDto);
        } catch (NoSuchElementException | NoResultException e) {
            throw new ScheduledTaskNotFoundException();
        }

    }
}
