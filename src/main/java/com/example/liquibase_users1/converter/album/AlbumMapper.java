package com.example.liquibase_users1.converter.album;

import com.example.liquibase_users1.converter.DtoMapper;
import com.example.liquibase_users1.converter.EntityMapper;
import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.entity.Album;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper extends DtoMapper<AlbumResponseDTO, Album>, EntityMapper<AlbumRequestDTO, Album> {
    AlbumResponseDTO toDto(Album a);

    List<AlbumResponseDTO> toDto(List<Album> list);

    Album toEntity(AlbumRequestDTO d);

    List<Album> toEntity(List<AlbumRequestDTO> dList);
}
