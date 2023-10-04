package com.example.liquibase_users1.service;

import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.request.PhotoRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Photo;
import jakarta.transaction.Transactional;

import java.util.List;

public interface AlbumService {


    AlbumResponseDTO getAlbumResponseDTO(long id);

    AlbumResponseDTO updateAlbum(long albumId, AlbumRequestDTO album);

    String deleteAlbum(long albumId);

    AlbumResponseDTO addPhoto(long albumId, PhotoRequestDTO photo);

    Photo getPhoto(long photoId);

    PhotoResponseDTO getPhotoResponseDTO(long photoId);

    AlbumResponseDTO deletePhotoUser(long albumId, long photoId);

    UserResponseDTO createProfilePictureUser(long id, long photoId);

    UserResponseDTO deleteAlbumUser(long id, Long albumId);

    AlbumResponseDTO createAlbumUser(long id, AlbumRequestDTO albumDTO);

    GroupResponseDTO createProfilePictureGroup(long id, long photoId);

    GroupResponseDTO deleteAlbumGroup(long id, Long albumId);

    AlbumResponseDTO createAlbumGroup(long id, AlbumRequestDTO albumDTO);

    List<PhotoResponseDTO> getAlbumList(long albumId);

    String deletePhoto(long id);
}
