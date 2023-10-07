package com.example.liquibase_users1.service;

import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.request.PhotoRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Album;
import com.example.liquibase_users1.models.entity.Photo;

import java.util.List;

public interface AlbumService {


    Album getAlbum(long albumId);

    AlbumResponseDTO getAlbumResponseDTO(long id);

    AlbumResponseDTO updateAlbum(long albumId, AlbumRequestDTO album);

    String deleteAlbum(long albumId);

    AlbumResponseDTO addPhoto(long albumId, PhotoRequestDTO photo);

    Photo getPhoto(long photoId);

    PhotoResponseDTO getPhotoResponseDTO(long photoId);

    UserResponseDTO createProfilePictureUser(long id, long photoId);

    AlbumResponseDTO createAlbumUser(long id, AlbumRequestDTO albumDTO);

    GroupResponseDTO createProfilePictureGroup(long id, long photoId);

    AlbumResponseDTO createAlbumGroup(long id, AlbumRequestDTO albumDTO);

    List<PhotoResponseDTO> getAlbumList(long albumId);

    String deletePhoto(long id);
}
