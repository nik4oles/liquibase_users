package com.example.liquibase_users1.models.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDTO {
    private String name;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String shortStoryAboutUser;
    private String number;
    private LocalDate dateOfBirth;
    private String gender;
    private String status;
    private LocationRequestDTO location;
    private boolean isPrivate;
}
