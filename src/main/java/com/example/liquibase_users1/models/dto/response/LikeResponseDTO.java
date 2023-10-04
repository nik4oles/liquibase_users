package com.example.liquibase_users1.models.dto.response;

import com.example.liquibase_users1.models.enums.Emotion;
import lombok.Data;

@Data
public class LikeResponseDTO {
    private Long id;
    private UserResponseDTO user;
    private Emotion emotion;
}
