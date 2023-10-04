package com.example.liquibase_users1.converter.group;

import com.example.liquibase_users1.converter.EntityMapper;
import com.example.liquibase_users1.models.dto.request.GroupRegistrationRequestDTO;
import com.example.liquibase_users1.models.entity.Group;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GroupRegistrationMapper extends EntityMapper<GroupRegistrationRequestDTO, Group> {
    @Override
    Group toEntity(GroupRegistrationRequestDTO d);
}
