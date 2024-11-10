package com.example.connectserver.domain.preference.repository;

import com.example.connectserver.domain.preference.entity.BookmarkEntity;
import com.example.connectserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    List<BookmarkEntity> findByUser(UserEntity user);

    Optional<BookmarkEntity> findById(Long bookmarkId);
}
