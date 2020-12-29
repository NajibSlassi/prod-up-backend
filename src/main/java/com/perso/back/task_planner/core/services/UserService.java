package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.exception.UserConstraintViolationException;
import com.perso.back.task_planner.exception.UserNotFoundException;
import com.perso.back.task_planner.infra.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository =userRepository;
    }

    public User getById(Integer id) throws UserNotFoundException {
        return userRepository.getById(id);
    }

    @Transactional
    public User getByEmail(String email) throws UserNotFoundException {
        return userRepository.getByEmail(email);
    }

    @Transactional
    public Integer create(User user) throws CustomMappingException, UserConstraintViolationException {
        return userRepository.save(user);
    }

    @Transactional
    public void update(User user) throws CustomMappingException, UserNotFoundException {
        User persistedUser = getById(user.getId());
        userRepository.update(user);
    }

    @Transactional
    public void deleteById(Integer id) throws UserNotFoundException {
        userRepository.delete(id);
    }


}
