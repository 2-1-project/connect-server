package com.example.connectserver.domain.user.entity;

import com.example.connectserver.domain.preference.entity.BookmarkEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instagram_id")
    private Long instagramId;

    @Column(name = "instagram_username")
    private String instagramUsername;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "")
    private List<BookmarkEntity> bookmarks;
}
