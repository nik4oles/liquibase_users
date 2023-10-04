package com.example.liquibase_users1.models.dto.response;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class AlbumResponseDTO {
    private long id;
    private String name;
    private String description;
    private LocalDate date_creation;
    private List<PhotoResponseDTO> photos;
}
