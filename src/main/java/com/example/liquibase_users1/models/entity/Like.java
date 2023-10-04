package com.example.liquibase_users1.models.entity;

import com.example.liquibase_users1.models.enums.Emotion;
import jakarta.persistence.*;
import lombok.Data;


@Entity
@Data
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private Emotion emotion;

    public Like() {
    }
}
