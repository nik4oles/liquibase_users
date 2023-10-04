package com.example.liquibase_users1.models.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AlbumRequestDTO {
    private String name;
    private LocalDate date_creation;
    private String description;
    private boolean isPrivate;
}
