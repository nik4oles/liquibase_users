package com.example.liquibase_users1.models.dto.request;

import lombok.Data;

@Data
public class AlbumRequestDTO {
    private String name;
    private String description;
    private boolean isPrivate;
}
