package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.infra.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository =userRepository;
    }

    public User getById(Integer id) throws Exception {
        return userRepository.getById(id);
    }

    @Transactional
    public User getByUserName(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Transactional
    public Integer create(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void update(User user) {
        userRepository.update(user);
    }

    @Transactional
    public void deleteById(Integer id) {
        userRepository.delete(id);
    }


}
