package com.example.connectserver.domain.user.repository;

import com.example.connectserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findByInstagramId(Long id);
    Optional<UserEntity> findByInstagramUsername(String instagramUsername);
}
