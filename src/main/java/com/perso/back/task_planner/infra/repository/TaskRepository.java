package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.Task;
import com.perso.back.task_planner.exception.TaskConstraintViolationException;
import com.perso.back.task_planner.exception.TaskNotFoundException;
import com.perso.back.task_planner.infra.dto.TaskDBDto;
import com.perso.back.task_planner.infra.mapper.TaskDBMapper;
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
import java.util.Optional;


@Repository
public class TaskRepository {

    private static final String QUERY_FIND_TASK_BY_ID =
            "FROM TaskDBDto as task "
                    + "where id = :id";
    private static final String QUERY_FIND_ALL_TASKS = "FROM TaskDBDto as task "
            + "where task.userDBDto.id = :userId";

    private static final String QUERY_UPDATE_ORDER_1_LOW_TO_HIGH = "UPDATE TaskDBDto set task_order = task_order - 1 "  +
            "WHERE task_order >= :old_task_order and task_order <= :new_task_order";
    private static final String QUERY_UPDATE_ORDER_1_HIGH_TO_LOW = "UPDATE TaskDBDto set task_order = task_order + 1 "  +
            "WHERE task_order <= :old_task_order and task_order >= :new_task_order";
    private static final String QUERY_UPDATE_ORDER_2 = "UPDATE TaskDBDto set task_order = :new_task_order "  +
            "WHERE id = :id";


    @PersistenceContext
    private final EntityManager entityManager;

    private final TaskDBMapper taskDBMapper;
    private final Logger logger = LoggerFactory.getLogger(TaskRepository.class);


    public TaskRepository(EntityManager entityManager, TaskDBMapper taskDBMapper) {
        this.entityManager = entityManager;
        this.taskDBMapper = taskDBMapper;
    }


    public Task getById(Integer id) throws TaskNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        Query<TaskDBDto> query = session.createQuery(QUERY_FIND_TASK_BY_ID, TaskDBDto.class);
        query.setParameter("id", id);

        TaskDBDto taskDBDto = null;
        try {
            taskDBDto = query.getSingleResult();
        } catch (NoResultException e) {
            throw new TaskNotFoundException();
        }

        Optional<Task> optionalTask = taskDBMapper.mapToTask(taskDBDto);
        session.evict(taskDBDto);
        return optionalTask.orElseThrow(TaskNotFoundException::new);
    }

    public List<Task> getAll(Integer userId) {
        Session session = entityManager.unwrap(Session.class);
        Query<TaskDBDto> query = session.createQuery(QUERY_FIND_ALL_TASKS, TaskDBDto.class);
        query.setParameter("userId", userId);

        return taskDBMapper.mapToTasks(query.getResultList());
    }

    public Integer save(Task task) throws TaskConstraintViolationException, CustomMappingException {

        Session session = entityManager.unwrap(Session.class);
        Optional<TaskDBDto> optionalTaskToCreate = taskDBMapper.mapToDto(task);

        if (optionalTaskToCreate.isPresent()) {
            TaskDBDto taskToCreate = optionalTaskToCreate.get();
            try {

                session.save(taskToCreate);
            } catch (ConstraintViolationException e) {
                session.clear();
                throw new TaskConstraintViolationException();
            }

            return taskDBMapper.mapToTask(taskToCreate).get().getId();
        } else {
            logger.debug("An optional task cannot be mapped : {}", optionalTaskToCreate);
            throw new CustomMappingException();
        }
    }


    public void update(Task task) throws CustomMappingException {
        Session session = entityManager.unwrap(Session.class);
        Optional<TaskDBDto> optionalTaskToUpdate = taskDBMapper.mapToDto(task);

        if (optionalTaskToUpdate.isPresent()) {
            TaskDBDto taskToUpdate = optionalTaskToUpdate.get();
            session.update(taskToUpdate);
        } else {
            logger.debug("An optional task cannot be mapped : {}", optionalTaskToUpdate);
            throw new CustomMappingException();
        }
    }

    public void delete(Integer id) throws TaskNotFoundException {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<TaskDBDto> queryGet = session.createQuery(QUERY_FIND_TASK_BY_ID, TaskDBDto.class);
            queryGet.setParameter("id", id);
            TaskDBDto taskDBDto = queryGet.getSingleResult();
            session.delete(taskDBDto);
        } catch (NoResultException e) {
            throw new TaskNotFoundException();
        }
    }

    public void updateOrder(Integer old_task_order, Integer new_task_order, Integer id) throws TaskNotFoundException {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<TaskDBDto> query1;
            if(old_task_order > new_task_order){
                query1 = session.createQuery(QUERY_UPDATE_ORDER_1_HIGH_TO_LOW);
            }else{
                query1 = session.createQuery(QUERY_UPDATE_ORDER_1_LOW_TO_HIGH);
            }
            query1.setParameter("old_task_order", old_task_order);
            query1.setParameter("new_task_order", new_task_order);
            query1.executeUpdate();
            Query<TaskDBDto> query2 = session.createQuery(QUERY_UPDATE_ORDER_2);
            query2.setParameter("id", id);
            query2.setParameter("new_task_order", new_task_order);
            query2.executeUpdate();
        } catch (NoResultException e) {
            throw new TaskNotFoundException();
        }
    }


}
