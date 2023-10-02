package com.example.liquibase_users1.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
public class Location {
    private String country;
    private String city;
}
