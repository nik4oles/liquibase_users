package com.example.liquibase_users1.converter.album;

import com.example.liquibase_users1.converter.DtoMapper;
import com.example.liquibase_users1.converter.EntityMapper;
import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.entity.Album;
import com.example.liquibase_users1.models.entity.Photo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AlbumMapper extends DtoMapper<AlbumResponseDTO, Album>, EntityMapper<AlbumRequestDTO, Album> {

    @Override
    default AlbumResponseDTO toDto(Album a) {
        if (a == null) {
            return null;
        }

        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO();

        albumResponseDTO.setId(a.getId());
        albumResponseDTO.setName(a.getName());
        albumResponseDTO.setDescription(a.getDescription());
        albumResponseDTO.setDate_creation(a.getDate_creation());

        List<Photo> list = a.getPhotos();

        List<PhotoResponseDTO> list1 = new ArrayList<PhotoResponseDTO>(list.size());

        for (Photo photo : list) {

            PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();
            photoResponseDTO.setId(photo.getId());
            photoResponseDTO.setUri(photo.getUri());
            photoResponseDTO.setAlbumId(a.getId());
            photoResponseDTO.setTitle(photo.getTitle());
            photoResponseDTO.setLike(photo.getLikes().size());
            photoResponseDTO.setPublicationDate(photo.getPublicationDate());

            list1.add(photoResponseDTO);
        }

        albumResponseDTO.setPhotos(list1);

        return albumResponseDTO;
    }

    List<AlbumResponseDTO> toDto(List<Album> list);

    Album toEntity(AlbumRequestDTO d);

    List<Album> toEntity(List<AlbumRequestDTO> dList);


}
