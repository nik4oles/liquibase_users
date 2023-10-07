package com.example.liquibase_users1.models.dto.response;

import lombok.Data;

import java.time.LocalDate;


@Data
public class UserResponseDTO {
    private long id;
    private PhotoResponseDTO profilePicture;
    private String name;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String shortStoryAboutUser;
    private short number;
    private LocalDate dateOfBirth;
    private String gender;
    private String status;
    private boolean isPrivate;

}
