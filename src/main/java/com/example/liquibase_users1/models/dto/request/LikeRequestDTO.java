package com.example.liquibase_users1.models.dto.request;

import com.example.liquibase_users1.models.enums.Emotion;
import lombok.Data;

@Data
public class LikeRequestDTO {
    private Emotion emotion;
}
