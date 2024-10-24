package com.example.connectserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private long id;

    @Column(name = "instagram_id", unique = true)
    private String instagramId;

    @Column(name = "instagram_username")
    private String instagramUsername;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;
}
