package com.finnect.auth.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpaEntity, Long> {

    Optional<UserJpaEntity> findByUsername(String username);
}
