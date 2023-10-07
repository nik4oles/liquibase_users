package com.example.liquibase_users1.converter.user;

import com.example.liquibase_users1.converter.DtoMapper;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper extends DtoMapper<UserResponseDTO, User> {
    @Override
    UserResponseDTO toDto(User user);

    @Override
    List<UserResponseDTO> toDto(List<User> list);

}
