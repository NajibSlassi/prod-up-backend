package com.perso.back.task_planner.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1)
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Advice qui log le début d'exécution d'une méthode.
     *
     * @param joinPoint représente une méthode
     */
    @Before("@annotation(com.perso.back.task_planner.aop.annotation.Logging)")
    public void logBefore(JoinPoint joinPoint) {
        LOGGER.debug(String.format("Entrée de la méthode '%s'",joinPoint.getSignature().getName()));
    }

    /**
     * Advice qui log la fin d'exécution d'une méthode.
     *
     * @param joinPoint représente une méthode
     */
    @After("@annotation(com.perso.back.task_planner.aop.annotation.Logging)")
    public void logAfter(JoinPoint joinPoint) {

        LOGGER.debug(String.format("Sortie de la méthode '%s'",joinPoint.getSignature().getName()));
    }
}
