package com.perso.back.task_planner.api.controller.v1;

import com.perso.back.task_planner.api.dto.UserApiDto;
import com.perso.back.task_planner.api.mapper.UserApiMapper;
import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.core.services.UserService;
import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.exception.UserConstraintViolationException;
import com.perso.back.task_planner.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/users")
class   UserController {
    @Autowired
    private UserService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserApiMapper userApiMapper;

    public UserController(UserApiMapper userApiMapper) {
        this.userApiMapper = userApiMapper;
    }

    @GetMapping(value = "/{id}")
    public UserApiDto findById(@PathVariable("id") Integer id) throws UserNotFoundException {
        User user = service.getById(id);
        Optional<UserApiDto> userApiDto = userApiMapper.mapToDto(user);
        return userApiDto.get();
    }

    @GetMapping(value = "/email/{email}")
    public UserApiDto findByEmail(@PathVariable("email") String email) throws UserNotFoundException {
        User user = service.getByEmail(email);
        Optional<UserApiDto> userApiDto = userApiMapper.mapToDto(user);
        return userApiDto.get();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody User user) throws CustomMappingException, UserConstraintViolationException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return service.create(user);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody User user) throws Exception {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        service.update(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void delete(@PathVariable("id") Integer id) throws UserNotFoundException {
        service.deleteById(id);
    }

}
