package com.example.liquibase_users1.models.dto.response;

import com.example.liquibase_users1.models.entity.Photo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GroupResponseDTO {
    private long id;
    private String name;
    private String description;
    private LocalDate reg_date;
    private UserResponseDTO owner;
    private int subscribersCount;
    private Photo profilePicture;
    private String shortStoryAboutGroup;
    private boolean isPrivate;
    private boolean isOfficialGroup;
}
