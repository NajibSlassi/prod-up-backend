package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.exception.UserConstraintViolationException;
import com.perso.back.task_planner.exception.UserNotFoundException;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.infra.mapper.UserDBMapper;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.NoSuchElementException;
import java.util.Optional;


@Repository
public class UserRepository {

    private static final String QUERY_FIND_USER_BY_ID =
            "FROM UserDBDto as user "
                    + "where id = :id";

    private static final String QUERY_FIND_USER_BY_EMAIL =
            "FROM UserDBDto as user "
                    + "where email = :email";

    @PersistenceContext
    private final EntityManager entityManager;
    private final UserDBMapper userDBMapper;
    private final Logger logger = LoggerFactory.getLogger(UserRepository.class);


    public UserRepository(EntityManager entityManager, UserDBMapper userDBMapper) {
        this.entityManager = entityManager;
        this.userDBMapper = userDBMapper;
    }


    public User getById(Integer id) throws UserNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        Query<UserDBDto> query = session.createQuery(QUERY_FIND_USER_BY_ID, UserDBDto.class);
        query.setParameter("id", id);

        UserDBDto userDBDto = null;
        try {
            userDBDto = query.getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException();
        }

        Optional<User> optionalUser = userDBMapper.mapToUser(userDBDto);
        session.evict(userDBDto);
        return optionalUser.get();
    }

    public User getByEmail(String email) throws UserNotFoundException {
        Session session = entityManager.unwrap(Session.class);
        Query<UserDBDto> query = session.createQuery(QUERY_FIND_USER_BY_EMAIL, UserDBDto.class);
        query.setParameter("email", email);
        UserDBDto userDBDto = null;
        try {
            userDBDto = query.getSingleResult();
        } catch (NoResultException e) {
            throw new UserNotFoundException();
        }

        Optional<User> optionalUser = userDBMapper.mapToUser(userDBDto);

        return optionalUser.get();
    }

    public Integer save(User user) throws CustomMappingException, UserConstraintViolationException {

        Session session = entityManager.unwrap(Session.class);
        Optional<UserDBDto> optionalUserToCreate = userDBMapper.mapToDto(user);

        if (optionalUserToCreate.isPresent()) {
            UserDBDto userToCreate = optionalUserToCreate.get();
            try {
                session.save(userToCreate);
            } catch (ConstraintViolationException e) {
                session.clear();
                throw new UserConstraintViolationException();
            }

            return userDBMapper.mapToUser(userToCreate).get().getId();
        } else {
            logger.debug("An optional user cannot be mapped : {}", optionalUserToCreate);
            throw new CustomMappingException();
        }
    }


    public void update(User user) throws CustomMappingException {
        Session session = entityManager.unwrap(Session.class);
        Optional<UserDBDto> optionalUserToUpdate = userDBMapper.mapToDto(user);

        if (optionalUserToUpdate.isPresent()) {
            UserDBDto userToCreate = optionalUserToUpdate.get();
            session.update(userToCreate);
        } else {
            logger.debug("An optional user cannot be mapped : {}", optionalUserToUpdate);
            throw new CustomMappingException();
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        try {
            Session session = entityManager.unwrap(Session.class);
            Query<UserDBDto> queryGet = session.createQuery(QUERY_FIND_USER_BY_ID, UserDBDto.class);
            queryGet.setParameter("id", id);
            UserDBDto userDBDto = queryGet.getSingleResult();
            session.delete(userDBDto);
        } catch (NoSuchElementException | NoResultException e) {
            throw new UserNotFoundException();
        }

    }


}
