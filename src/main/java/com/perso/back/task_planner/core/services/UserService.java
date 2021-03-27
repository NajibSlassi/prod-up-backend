package com.perso.back.task_planner.core.services;

import com.perso.back.task_planner.api.dto.UserDto;
import com.perso.back.task_planner.exception.CustomMappingException;
import com.perso.back.task_planner.core.model.User;
import com.perso.back.task_planner.exception.UserConstraintViolationException;
import com.perso.back.task_planner.exception.UserNotFoundException;
import com.perso.back.task_planner.infra.dto.PasswordResetToken;
import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.infra.dto.VerificationToken;
import com.perso.back.task_planner.infra.mapper.UserDBMapper;
import com.perso.back.task_planner.infra.repository.PasswordResetTokenRepository;
import com.perso.back.task_planner.infra.repository.RoleRepository;
import com.perso.back.task_planner.infra.repository.UserRepository;
import com.perso.back.task_planner.infra.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {

    private UserDBMapper userDBMapper;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public UserService(UserDBMapper userDBMapper, UserRepository userRepository, RoleRepository roleRepository,
                       PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userDBMapper = userDBMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenRepository tokenRepository;

    public User getById(Integer id) throws UserNotFoundException {
        return userRepository.getById(id);
    }

    @Transactional
    public UserDBDto getByEmail(String email) throws UserNotFoundException {
        return userRepository.getByEmail(email);
    }

    @Transactional
    public UserDBDto getUserDBDtoByEmail(String email) throws UserNotFoundException {
        return userRepository.getUserDBDtoByEmail(email);
    }

    @Transactional
    public Integer create(User user) throws CustomMappingException, UserConstraintViolationException {
        return userRepository.save(user);
    }

    @Transactional
    public void update(UserDBDto userDBDto) throws CustomMappingException, UserNotFoundException {
        User persistedUser = getById(userDBDto.getId());
        userRepository.update(userDBDto);
    }

    @Transactional
    public void deleteById(Integer id) throws UserNotFoundException {
        userRepository.delete(id);
    }

    @Transactional
    public UserDBDto registerNewUserAccount(final UserDto accountDto) throws CustomMappingException, UserConstraintViolationException, UserNotFoundException {
        final UserDBDto userDBDto = new UserDBDto();

        if (emailExists(accountDto.getEmail())) {
            throw new UserConstraintViolationException();
        }

        userDBDto.setFirstName(accountDto.getFirstName());
        userDBDto.setLastName(accountDto.getLastName());
        userDBDto.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        userDBDto.setEmail(accountDto.getEmail());
        userDBDto.setRoles(Arrays.asList(roleRepository.getByName("ROLE_USER")));
        User user = userDBMapper.mapToUser(userDBDto).orElseGet(null);
        Integer id = userRepository.save(user);
        userDBDto.setId(id);

        return userDBDto;
    }
    @Transactional
    public void createVerificationToken(UserDBDto userDBDto, String token) {
        VerificationToken myToken = new VerificationToken(token, userDBDto);
        tokenRepository.save(myToken);
    }

    private boolean emailExists(final String email) {

        try {
            return userRepository.getByEmail(email) != null;
        } catch (UserNotFoundException e) {
            return false;
        }

    }

    @Transactional
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Transactional
    public void saveRegisteredUser(final UserDBDto userDBDto) throws CustomMappingException, UserConstraintViolationException {
        //User user = userDBMapper.mapToUser(userDBDto).orElseGet(null);
        userRepository.saveDto(userDBDto);
    }

    public Optional<UserDBDto> getUserByPasswordResetToken(final String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getUserDBDto());
    }

    public void createPasswordResetTokenForUser(final UserDBDto userDBDto, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, userDBDto);
        passwordResetTokenRepository.save(myToken);
    }

    public boolean checkIfValidOldPassword(final UserDBDto userDBDto, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, userDBDto.getPassword());
    }

    @Transactional
    public void changeUserPassword(final UserDBDto userDBDto, final String password) throws CustomMappingException, UserConstraintViolationException {
        userDBDto.setPassword(passwordEncoder.encode(password));
        userRepository.saveDto(userDBDto);
    }

}
