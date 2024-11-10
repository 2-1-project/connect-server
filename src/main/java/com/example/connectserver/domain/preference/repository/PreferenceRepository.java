package com.example.connectserver.domain.preference.repository;

import com.example.connectserver.domain.preference.entity.PreferenceEntity;
import com.example.connectserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreferenceRepository extends JpaRepository<PreferenceEntity, Long> {
    List<PreferenceEntity> findByUser(UserEntity user);
}
