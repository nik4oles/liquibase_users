package com.example.liquibase_users1.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String country;
    private String city;
}
