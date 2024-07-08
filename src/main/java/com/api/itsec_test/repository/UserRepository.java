package com.api.itsec_test.repository;

import com.api.itsec_test.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    Optional<UserModel> findByUsername(String username);
    Boolean existsByUsername(String username);
}
