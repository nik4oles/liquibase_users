package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import com.example.liquibase_users1.converter.album.AlbumMapper;
import com.example.liquibase_users1.converter.album.AlbumMapperImpl;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.converter.group.GroupMapperImpl;
import com.example.liquibase_users1.converter.photo.PhotoMapper;
import com.example.liquibase_users1.converter.photo.PhotoMapperImpl;
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
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AlbumServiceImplTest {
    @Mock
    private AlbumRepository albumRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private UserServiceImpl userService;
    @Mock
    private GroupServiceImpl groupService;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private AlbumMapperImpl albumMapper;
    @Mock
    private PhotoMapperImpl photoMapper;
    @Mock
    private GroupMapperImpl groupMapper;

    @InjectMocks
    private AlbumServiceImpl albumService;


    @Test
    void getAlbum() {
        // Arrange
        long albumId = 1L;
        Album expectedAlbum = new Album();
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(expectedAlbum));

        // Act
        Album actualAlbum = albumService.getAlbum(albumId);

        // Assert
        assertEquals(expectedAlbum, actualAlbum);
    }
    @Test
    public void getAlbumFailed() {
        // Arrange
        long albumId = 1L;
        when(albumRepository.findById(albumId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> albumService.getAlbum(albumId));
    }

    @Test
    void getAlbumResponseDTO() {
        long albumId = 1L;
        Album album = new Album();
        AlbumResponseDTO expectedResponseDTO = new AlbumResponseDTO();
        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));
        when(albumMapper.toDto(album)).thenReturn(expectedResponseDTO);

        AlbumResponseDTO result = albumService.getAlbumResponseDTO(albumId);


        assertEquals(expectedResponseDTO, result);
        verify(albumRepository, times(1)).findById(albumId);
        verify(albumMapper, times(1)).toDto(album);
    }


    @Test
    public void testUpdateAlbum() {
        long albumId = 1;
        AlbumRequestDTO albumRequest = new AlbumRequestDTO();
        albumRequest.setName("New album name");
        albumRequest.setPrivate(true);
        albumRequest.setDescription("New album description");

        Album albumBD = new Album();
        albumBD.setId(albumId);
        albumBD.setName("Old album name");
        albumBD.setPrivate(false);
        albumBD.setDescription("Old album description");

        AlbumResponseDTO expectedResponse = new AlbumResponseDTO();
        expectedResponse.setId(albumId);
        expectedResponse.setName("New album name");
        expectedResponse.setDescription("New album description");


        // Установка поведения макета для getById метода
        when(albumRepository.getAlbumById(albumId)).thenReturn(albumBD);

        when(albumRepository.save(albumBD)).thenReturn(albumBD);
        // Установка поведения макета для toDto метода
        when(albumMapper.toDto(any(Album.class))).thenReturn(expectedResponse);

        // Выполнение метода
        AlbumResponseDTO actualResponse = albumService.updateAlbum(albumId, albumRequest);

        // Проверка результата
        assertEquals(expectedResponse, actualResponse);

        // Проверка взаимодействия с макетами
        verify(albumRepository).getAlbumById(albumId);
        verify(albumRepository).save(albumBD);
        verify(albumMapper).toDto(albumBD);
    }


    @Test
    void deleteAlbum() {
        long albumId = 1L;
        Album album = new Album();
        album.setName("name");


        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));

        String response = albumService.deleteAlbum(albumId);

        assertEquals(response, "Альбом удален");

        verify(albumRepository, times(1)).findById(albumId);
    }

    @Test
    void addPhoto() {
        long albumId = 1L;

        AlbumResponseDTO albumResponse = new AlbumResponseDTO();
        albumResponse.setId(albumId);
        albumResponse.setName("New album name");
        albumResponse.setDescription("New album description");

        PhotoRequestDTO photoRequestDTO = new PhotoRequestDTO();
        Album album = new Album();
        Photo photo = new Photo();
        List<Photo> photos = new ArrayList<>();
        album.setPhotos(photos);


        when(albumRepository.findById(albumId)).thenReturn(Optional.of(album));
        when(photoMapper.toEntity(photoRequestDTO)).thenReturn(photo);
        when(albumRepository.save(album)).thenReturn(album);
        when(photoRepository.save(photo)).thenReturn(photo);
        when(albumMapper.toDto(album)).thenReturn(albumResponse);


        AlbumResponseDTO result = albumService.addPhoto(albumId, photoRequestDTO);

        assertEquals(result, albumResponse);

    }

    @Test
    void getPhoto() {
        long photoId = 1L;

        Album albumBD = new Album();
        albumBD.setId(1L);
        albumBD.setName("Old album name");
        albumBD.setPrivate(false);
        albumBD.setDescription("Old album description");

        Photo photo = new Photo();
        photo.setAlbum(albumBD);
        photo.setId(1L);
        photo.setTitle("title");

        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        Photo photoService = albumService.getPhoto(photoId);

        assertEquals(photo, photoService);
    }

    @Test
    void getPhotoResponseDTO() {
        long photoId = 1L;

        PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();
        photoResponseDTO.setTitle("title");

        Photo photo = new Photo();


        when(photoMapper.toDto(photo)).thenReturn(photoResponseDTO);
        when(photoRepository.findById(photoId)).thenReturn(Optional.of(photo));

        PhotoResponseDTO photoResponseDTO1 = albumService.getPhotoResponseDTO(photoId);

        assertEquals(photoResponseDTO1, photoResponseDTO);
    }


    @Test
    void createProfilePictureUser() {
        // Arrange
        long userId = 1;
        long photoId = 2;

        User user = new User();
        user.setId(userId);

        Photo photo = new Photo();
        photo.setId(photoId);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(userId);

        when(userService.getUser(userId)).thenReturn(user);
        when(photoRepository.getPhotoById(photoId)).thenReturn(photo);
        when(userService.getUserResponseDTO(userId)).thenReturn(userResponseDTO);

        // Act
        UserResponseDTO result = albumService.createProfilePictureUser(userId, photoId);

        // Assert
        assertEquals(result, userResponseDTO);

    }


    @Test
    void createAlbumUser() {
        // Arrange
        long userId = 1;
        AlbumRequestDTO albumDTO = new AlbumRequestDTO();
        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO();
        Album album = new Album();
        List<Album> list = new ArrayList<>();

        album.setId(1L);
        albumResponseDTO.setId(1L);

        User user = new User();
        user.setId(userId);

        when(albumMapper.toEntity(albumDTO)).thenReturn(album);
        when(userService.getUser(userId)).thenReturn(user);
        when(albumRepository.save(album)).thenReturn(album);
        when(albumMapper.toDto(album)).thenReturn(albumResponseDTO);
        // Act
        AlbumResponseDTO result = albumService.createAlbumUser(userId, albumDTO);

        // Assert
        assertEquals(albumResponseDTO, result);
        verify(albumMapper, times(1)).toEntity(albumDTO);
        verify(userService, times(1)).getUser(userId);
        verify(albumRepository, times(1)).save(album);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void createProfilePictureGroup() {
        // Arrange
        long groupId = 1;
        long photoId = 2;

        Group group = new Group();
        group.setId(groupId);

        Photo photo = new Photo();
        photo.setId(photoId);

        GroupResponseDTO groupResponseDTO = new GroupResponseDTO();
        groupResponseDTO.setId(groupId);

        when(groupService.getGroup(groupId)).thenReturn(group);
        when(photoRepository.getPhotoById(photoId)).thenReturn(photo);
        when(groupMapper.toDto(group)).thenReturn(groupResponseDTO);
        when(groupRepository.save(group)).thenReturn(group);

        // Act
        GroupResponseDTO result = albumService.createProfilePictureGroup(groupId, photoId);

        // Assert
        assertEquals(result, groupResponseDTO);
    }


    @Test
    void createAlbumGroup() {
        // Arrange
        long groupId = 1;
        AlbumRequestDTO albumDTO = new AlbumRequestDTO();
        AlbumResponseDTO albumResponseDTO = new AlbumResponseDTO();
        Album album = new Album();
        List<Album> list = new ArrayList<>();

        album.setId(1L);
        albumResponseDTO.setId(1L);

        Group group = new Group();
        group.setId(groupId);

        when(albumMapper.toEntity(albumDTO)).thenReturn(album);
        when(groupService.getGroup(groupId)).thenReturn(group);
        when(albumRepository.save(album)).thenReturn(album);
        when(albumMapper.toDto(album)).thenReturn(albumResponseDTO);
        // Act
        AlbumResponseDTO result = albumService.createAlbumGroup(groupId, albumDTO);

        // Assert
        assertEquals(albumResponseDTO, result);

    }

    @Test
    void getAlbumList() {
        long albumId = 1L;
        List<Photo> photos = new ArrayList<>();
        List<PhotoResponseDTO> photoResponseDTOS = new ArrayList<>();

        Album album = new Album();
        album.setId(albumId);

        album.setId(albumId);

        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo();
            PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();

            photo.setAlbum(album);
            photoResponseDTO.setAlbumId(albumId);

            photos.add(photo);
            photoResponseDTOS.add(photoResponseDTO);

        }
        when(photoRepository.getPhotoByAlbum_Id(albumId)).thenReturn(photos);
        when(photoMapper.toDto(photos)).thenReturn(photoResponseDTOS);

        List<PhotoResponseDTO> result = albumService.getAlbumList(albumId);

        assertEquals(photoResponseDTOS, result);

    }

    @Test
    void deletePhoto() {
        // Arrange
        long id = 1L;

        // Act
        String result = albumService.deletePhoto(id);

        // Assert
        assertEquals("Фото удалено", result);
        verify(photoRepository, times(1)).deleteById(id);
    }
}