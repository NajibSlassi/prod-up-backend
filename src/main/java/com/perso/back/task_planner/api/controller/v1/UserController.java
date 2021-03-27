package com.perso.back.task_planner.api.controller.v1;

import com.perso.back.task_planner.aop.security.UserDetailsExtended;
import com.perso.back.task_planner.aop.security.UserSecurityService;
import com.perso.back.task_planner.api.dto.UserApiDto;
import com.perso.back.task_planner.api.dto.UserDto;
import com.perso.back.task_planner.api.mapper.UserApiMapper;
import com.perso.back.task_planner.core.services.UserService;
import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.exception.InvalidOldPasswordException;
import com.perso.back.task_planner.exception.UserConstraintViolationException;
import com.perso.back.task_planner.exception.UserNotFoundException;
import com.perso.back.task_planner.infra.dto.PasswordDto;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.infra.dto.VerificationToken;
import com.perso.back.task_planner.infra.mapper.UserDBMapper;
import com.perso.back.task_planner.registration.OnRegistrationCompleteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/users")
class   UserController {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private UserService userService;
    private UserApiMapper userApiMapper;
    private UserDBMapper userDBMapper;
    private UserSecurityService userSecurityService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ApplicationEventPublisher eventPublisher;

    @Autowired
    private Environment env;


    public UserController(UserService userService, UserApiMapper userApiMapper, UserDBMapper userDBMapper, UserSecurityService userSecurityService) {
        this.userApiMapper = userApiMapper;
        this.userService = userService;
        this.userDBMapper = userDBMapper;
        this.userSecurityService= userSecurityService;
    }

    @GetMapping(value = "/{email}")
    @CrossOrigin
    public UserApiDto findByEmail(@PathVariable("email") String email) throws UserNotFoundException {
        UserApiDto userApiDto = userApiMapper.mapToDto(userDBMapper.mapToUser(userService.getByEmail(email)).orElseGet(null)).orElseGet(null);
        return userApiDto;
    }

    @PutMapping(value = "/user/{email}")
    @ResponseStatus(HttpStatus.OK)
    @CrossOrigin
    public void update(@PathVariable( "email" ) String email, @RequestBody UserApiDto userApiDto) throws Exception {

       UserDBDto userDB =  userService.getByEmail(email);
       if(userApiDto.getFirstName() != null){
           userDB.setFirstName(userApiDto.getFirstName());
       }

        if(userApiDto.getLastName() != null){
            userDB.setLastName(userApiDto.getLastName());
        }

        if(userApiDto.getFirstName() != null){
            userDB.setEmail(userApiDto.getEmail());
        }

        userService.update(userDB);
    }
    

    // Registration
    @PostMapping("/registration")
    @CrossOrigin
    public Integer registerUserAccount(@RequestBody @Valid final UserDto accountDto, final HttpServletRequest request) throws CustomMappingException, UserConstraintViolationException, UserNotFoundException {
        LOGGER.debug("Registering user account with information: {}", accountDto);
        final UserDBDto registered = userService.registerNewUserAccount(accountDto);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return registered.getId();
    }

    @GetMapping("/registrationConfirm")
    @CrossOrigin
    public String confirmRegistration(final HttpServletRequest request, final Model model, @RequestParam("token") final String token) throws CustomMappingException, UserConstraintViolationException {
        final Locale locale = request.getLocale();

        final VerificationToken verificationToken = userService.getVerificationToken(token);
        if (verificationToken == null) {
            final String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        final UserDBDto userDBDto = verificationToken.getUserDBDto();
        final Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", messages.getMessage("auth.message.expired", null, locale));
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "redirect:/badUser.html?lang=" + locale.getLanguage();
        }

        userDBDto.setEnabled(true);
        userService.saveRegisteredUser(userDBDto);

        //model.addAttribute("message", messages.getMessage("message.accountVerified", null, locale));
        return "redirect:/login.html?lang=" + locale.getLanguage();
    }

    @PostMapping("/resetPassword")
    @CrossOrigin
    public ResponseEntity<?> resetPassword(HttpServletRequest request,
                                        @RequestParam("email") String userEmail) throws UserNotFoundException {
        UserDBDto userDBDto = userService.getByEmail(userEmail);
        if (userDBDto == null) {
            throw new UserNotFoundException();
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(userDBDto, token);
        SimpleMailMessage msg = constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, userDBDto);
                //      request.getLocale(), token, user)
                //mailSender.send(constructResetTokenEmail(getAppUrl(request),
                //      request.getLocale(), token, user));
        System.out.println(msg.getText());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    // Save password
    @PostMapping("/user/savePassword")
    @CrossOrigin
    public ResponseEntity<?> savePassword(final Locale locale, @Valid PasswordDto passwordDto) throws CustomMappingException, UserConstraintViolationException {

        final String result = userSecurityService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            //return new GenericResponse(messages.getMessage("auth.message." + result, null, locale));
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Optional<UserDBDto> optionaUserDBDto = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if(optionaUserDBDto.isPresent()) {
            userService.changeUserPassword(optionaUserDBDto.get(), passwordDto.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            //return new GenericResponse(messages.getMessage("auth.message.invalid", null, locale));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Change user password
    @PostMapping("/updatePassword")
    @CrossOrigin
    public ResponseEntity<?> changeUserPassword(final Locale locale, @Valid PasswordDto passwordDto) throws UserNotFoundException, CustomMappingException, UserConstraintViolationException {
        final UserDBDto userDBDto = userService.getUserDBDtoByEmail(((UserDetailsExtended) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        if (!userService.checkIfValidOldPassword(userDBDto, passwordDto.getOldPassword())) {
            throw new InvalidOldPasswordException();
        }
        userService.changeUserPassword(userDBDto, passwordDto.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    private SimpleMailMessage constructEmail(String subject, String body, UserDBDto userDBDto) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(userDBDto.getEmail());
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private SimpleMailMessage constructResetTokenEmail(final String contextPath, final Locale locale, final String token, final UserDBDto userDBDto) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = messages.getMessage("message.resetPassword", null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, userDBDto);
    }
}
