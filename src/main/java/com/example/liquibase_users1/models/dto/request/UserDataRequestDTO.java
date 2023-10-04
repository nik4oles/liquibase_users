package com.example.liquibase_users1.models.dto.request;

import lombok.Data;

@Data
public class UserDataRequestDTO {
    private String nickname;
    private String email;
    private String password;
    private String number;
}
