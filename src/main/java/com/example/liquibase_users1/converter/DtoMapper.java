package com.example.liquibase_users1.converter;

import java.util.List;

public interface DtoMapper<D, E> {
    D toDto(E e);

    List<D> toDto(List<E> eList);
}
