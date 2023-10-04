package com.example.liquibase_users1.converter.user;


import com.example.liquibase_users1.converter.EntityMapper;
import com.example.liquibase_users1.models.dto.request.UserRegistrationRequestDTO;
import com.example.liquibase_users1.models.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRegistrationMapper extends EntityMapper<UserRegistrationRequestDTO, User> {
    @Override
    User toEntity(UserRegistrationRequestDTO userDTO);
}
