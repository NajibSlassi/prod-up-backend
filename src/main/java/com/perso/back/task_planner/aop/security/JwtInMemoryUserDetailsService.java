package com.perso.back.task_planner.aop.security;

import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JwtInMemoryUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    public JwtInMemoryUserDetailsService(UserRepository userRepository) {
        this.userRepository =userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserPrincipal(user);
    }

}
