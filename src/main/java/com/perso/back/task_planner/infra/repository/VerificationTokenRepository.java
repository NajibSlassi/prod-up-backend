package com.perso.back.task_planner.infra.repository;

import com.perso.back.task_planner.infra.dto.UserDBDto;
import com.perso.back.task_planner.infra.dto.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository
        extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByToken(String token);

    VerificationToken findByUserDBDto(UserDBDto userDBDto);
}
