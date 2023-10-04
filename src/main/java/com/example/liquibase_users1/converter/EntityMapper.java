package com.example.liquibase_users1.converter;

import java.util.List;

public interface EntityMapper<D, E> {
    E toEntity(D d);

    List<E> toEntity(List<D> dList);
}