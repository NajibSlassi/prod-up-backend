package com.perso.back.task_planner.registration.listener;

import java.util.UUID;

import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.core.services.UserService;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.registration.OnRegistrationCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    // API

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final UserDBDto user = event.getUserDBDto();
        final String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        final SimpleMailMessage email = constructEmailMessage(event, user, token);
        System.out.println(email.getText() + "hahahaha");
        //mailSender.send(email);
    }

    private SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final UserDBDto userDBDto, final String token) {
        final String recipientAddress = userDBDto.getEmail();
        final String subject = "Registration Confirmation";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
        final String message = messages.getMessage("message.regSuccLink", null, "You registered successfully. To confirm your registration, please click on the below link.", event.getLocale());
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " \r\n" + confirmationUrl);
        email.setFrom(env.getProperty("support.email"));
        return email;
    }


}
