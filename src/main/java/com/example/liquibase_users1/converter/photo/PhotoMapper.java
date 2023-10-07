package com.example.liquibase_users1.converter.photo;

import com.example.liquibase_users1.converter.DtoMapper;
import com.example.liquibase_users1.converter.EntityMapper;
import com.example.liquibase_users1.models.dto.request.PhotoRequestDTO;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.entity.Photo;
import com.example.liquibase_users1.models.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhotoMapper extends DtoMapper<PhotoResponseDTO, Photo>, EntityMapper<PhotoRequestDTO, Photo> {
    //PhotoMapper PHOTO_MAPPER = Mappers.getMapper(PhotoMapper.class);

    @Override
    default PhotoResponseDTO toDto(Photo photo) {
        return new PhotoResponseDTO(
                photo.getId(),
                photo.getAlbum().getId(),
                photo.getUri(),
                photo.getTitle(),
                photo.getLikes().size(),
                photo.getTagUser().stream().map(User::getNickname).toList(),
                photo.getPublicationDate()
        );
    }

    List<PhotoResponseDTO> toDto(List<Photo> list);

    @Override
    Photo toEntity(PhotoRequestDTO d);
}
