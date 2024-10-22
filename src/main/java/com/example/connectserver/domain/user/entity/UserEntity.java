package com.example.connectserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private String instagramId;
    private String instagramUsername;
    private String username;
    private String password;
    private Date birth;

}
