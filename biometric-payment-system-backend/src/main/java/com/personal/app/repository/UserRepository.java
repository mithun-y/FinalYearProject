package com.personal.app.repository;
import com.personal.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);

    User findByAccountNumber(String accountNum);

    Optional<User> findByPhNumber(String phoneNumber);

}