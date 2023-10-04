package com.example.liquibase_users1.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
public class PhotoResponseDTO {
    private long id;
    private long albumId;
    private URI uri;
    private String title;
    private int like;
    private List<String> tagUserByNickName;
    private LocalDate publicationDate;
}
