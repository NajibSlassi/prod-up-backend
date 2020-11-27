package com.perso.back.task_planner.api.controller.v1;

import com.google.common.base.Preconditions;
import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.core.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
class   UserController {
    @Autowired
    private UserService service;

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id") Integer id) throws Exception {
        return RestPreconditions.checkFound(service.getById(id));
    }

    @GetMapping(value = "/{username}")
    public User findByUserName(@PathVariable("username") String userName) throws Exception {
        return RestPreconditions.checkFound(service.getByUserName(userName));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CrossOrigin
    public Integer create(@RequestBody User user) {
        Preconditions.checkNotNull(user);
        return service.create(user);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody User user) throws Exception {
        Preconditions.checkNotNull(user);
        Preconditions.checkNotNull(service.getById(user.getId()));
        service.update(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
    }

}
