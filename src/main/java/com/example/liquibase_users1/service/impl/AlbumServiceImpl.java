package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.converter.album.AlbumMapper;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.converter.photo.PhotoMapper;
import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.request.PhotoRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Album;
import com.example.liquibase_users1.models.entity.Group;
import com.example.liquibase_users1.models.entity.Photo;
import com.example.liquibase_users1.models.entity.User;
import com.example.liquibase_users1.repository.AlbumRepository;
import com.example.liquibase_users1.repository.GroupRepository;
import com.example.liquibase_users1.repository.PhotoRepository;
import com.example.liquibase_users1.repository.UserRepository;
import com.example.liquibase_users1.service.AlbumService;
import com.example.liquibase_users1.service.GroupService;
import com.example.liquibase_users1.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AlbumServiceImpl implements AlbumService {
    private final PhotoRepository photoRepository;
    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final UserService userService;
    private final GroupService groupService;
    private final GroupRepository groupRepository;

    private final AlbumMapper albumMapper;
    private final GroupMapper groupMapper;
    private final PhotoMapper photoMapper;

    @Override
    public Album getAlbum(long albumId) {
        return albumRepository.findById(albumId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public AlbumResponseDTO getAlbumResponseDTO(long id) {
        return albumMapper.toDto(albumRepository.getAlbumById(id));
    }

    @Override
    @Transactional
    public AlbumResponseDTO updateAlbum(long albumId, AlbumRequestDTO album) {
        Album albumBD = albumRepository.getAlbumById(albumId);
        albumBD.setName(album.getName());
        albumBD.setPrivate(album.isPrivate());
        albumBD.setDescription(album.getDescription());
        return albumMapper.toDto(albumRepository.save(albumBD));
    }

    @Override
    public String deleteAlbum(long albumId) {
        Album album = getAlbum(albumId);
        if (album.getName() == "main") {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        albumRepository.delete(album);
        return "Альбом удален";
    }

    @Override
    @Transactional
    public AlbumResponseDTO addPhoto(long albumId, PhotoRequestDTO photoRequestDTO) {
        Photo photo = photoMapper.toEntity(photoRequestDTO);
        photo.createNewPhoto();
        Album album = getAlbum(albumId);
        photo.setAlbum(album);
        //album.getPhotos().add(photo);
        //albumRepository.save(album);
        photoRepository.save(photo);
        return getAlbumResponseDTO(albumId);
    }

    @Override
    public Photo getPhoto(long photoId) {
        return photoRepository.findById(photoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public PhotoResponseDTO getPhotoResponseDTO(long photoId) {
        return photoMapper.toDto(photoRepository.getPhotoById(photoId));
    }


    @Override
    public UserResponseDTO createProfilePictureUser(long id, long photoId) {
        User user = userService.getUser(id);

        Photo photo = photoRepository.getPhotoById(photoId);
        user.setProfilePicture(photo);

        userRepository.save(user);
        return userService.getUserResponseDTO(id);
    }


    @Override
    @Transactional
    public AlbumResponseDTO createAlbumUser(long id, AlbumRequestDTO albumDTO) {
        Album album = albumMapper.toEntity(albumDTO);
        album.createNewAlbum();
        User user = userService.getUser(id);
        album = albumRepository.save(album);
        user.getAlbums().add(album);
        userRepository.save(user);
        return albumMapper.toDto(album);
    }

    @Override
    public GroupResponseDTO createProfilePictureGroup(long id, long photoId) {
        Group group = groupService.getGroup(id);

        Photo photo = photoRepository.getPhotoById(photoId);
        group.setProfilePicture(photo);

        return groupMapper.toDto(groupRepository.save(group));
    }

    @Override
    @Transactional
    public AlbumResponseDTO createAlbumGroup(long id, AlbumRequestDTO albumDTO) {
        Album album = albumMapper.toEntity(albumDTO);
        album.createNewAlbum();
        Group group = groupService.getGroup(id);
        album = albumRepository.save(album);
        group.getAlbums().add(album);
        return albumMapper.toDto(album);
    }

    @Override
    public List<PhotoResponseDTO> getAlbumList(long albumId) {
        return photoMapper.toDto(photoRepository.getPhotoByAlbum_Id(albumId));
    }

    @Override
    public String deletePhoto(long id) {
        photoRepository.deleteById(id);
        return "Фото удалено";
    }

}
