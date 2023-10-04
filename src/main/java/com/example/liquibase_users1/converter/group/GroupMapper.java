package com.example.liquibase_users1.converter.group;

import com.example.liquibase_users1.converter.DtoMapper;
import com.example.liquibase_users1.converter.EntityMapper;
import com.example.liquibase_users1.models.dto.request.GroupRequestDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.entity.Group;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper extends DtoMapper<GroupResponseDTO, Group>, EntityMapper<GroupRequestDTO, Group> {
    GroupResponseDTO toDto(Group e);

    List<GroupResponseDTO> toDto(List<Group> list);

    @Override
    Group toEntity(GroupRequestDTO groupRequestDTO);

    List<Group> toEntity(List<GroupRequestDTO> list);
}
