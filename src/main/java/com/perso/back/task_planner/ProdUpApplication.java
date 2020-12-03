package com.perso.back.task_planner;

import com.perso.back.task_planner.core.services.TaskService;
import com.perso.back.task_planner.infra.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
public class ProdUpApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProdUpApplication.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
