package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import com.example.liquibase_users1.converter.album.AlbumMapper;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.converter.user.UserMapper;
import com.example.liquibase_users1.converter.user.UserRegistrationMapper;
import com.example.liquibase_users1.models.dto.request.LocationRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserDataRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Album;
import com.example.liquibase_users1.models.entity.Group;
import com.example.liquibase_users1.models.entity.Location;
import com.example.liquibase_users1.models.entity.User;
import com.example.liquibase_users1.repository.AlbumRepository;
import com.example.liquibase_users1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AlbumRepository albumRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRegistrationMapper userRegistrationMapper;

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private AlbumMapper albumMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser() {
        // Arrange
        UserRegistrationRequestDTO userDTO = new UserRegistrationRequestDTO();
        User user = new User();
        Album album = new Album();
        User savedUser = new User();
        UserResponseDTO expectedResponseDTO = new UserResponseDTO();
        when(userRegistrationMapper.toEntity(userDTO)).thenReturn(user);
        when(albumRepository.save(album)).thenReturn(album);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toDto(savedUser)).thenReturn(expectedResponseDTO);

        // Act
        UserResponseDTO responseDTO = userService.createUser(userDTO);

        // Assert
        assertEquals(expectedResponseDTO, responseDTO);
    }

    @Test
    void getUser() {
        // Arrange
        long id = 1L;
        User expectedUser = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUser(id);

        // Assert
        assertEquals(expectedUser, actualUser);
        verify(userRepository).findById(id);
    }

    @Test
    void getUserResponseDTO() {
        // Arrange
        long id = 1L;
        User user = new User();
        UserResponseDTO expectedResponseDTO = new UserResponseDTO();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(expectedResponseDTO);

        // Act
        UserResponseDTO responseDTO = userService.getUserResponseDTO(id);

        // Assert
        assertEquals(expectedResponseDTO, responseDTO);
        verify(userRepository).findById(id);
        verify(userMapper).toDto(user);
    }

    @Test
    void getUserResponseDTOByNickname() {
        // Arrange
        String nickname = "john";
        User user = new User();
        UserResponseDTO expectedResponseDTO = new UserResponseDTO();

        when(userRepository.getUserByNickname(nickname)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedResponseDTO);

        // Act
        UserResponseDTO responseDTO = userService.getUserResponseDTOByNickname(nickname);

        // Assert
        assertEquals(expectedResponseDTO, responseDTO);
        verify(userRepository).getUserByNickname(nickname);
        verify(userMapper).toDto(user);
    }

    @Test
    void updateUser() {
        // Arrange
        long id = 1L;
        UserRequestDTO userDTO = new UserRequestDTO();
        User userBD = new User();
        String gender = "NOT_SPECIFIED";
        Location location = new Location();
        LocationRequestDTO location1 = new LocationRequestDTO();
        userBD.setLocation(location);
        userDTO.setLocation(location1);
        UserResponseDTO expectedResponseDTO = new UserResponseDTO();
        userDTO.setLocation(location1);
        userDTO.setGender(gender);
        when(userRepository.findById(id)).thenReturn(Optional.of(userBD));
        when(userRepository.save(userBD)).thenReturn(userBD);
        when(userMapper.toDto(userBD)).thenReturn(expectedResponseDTO);

        // Act
        UserResponseDTO responseDTO = userService.updateUser(userDTO, id);

        // Assert
        assertEquals(expectedResponseDTO, responseDTO);
    }

    @Test
    void updateData() {
        // Arrange
        long id = 1L;
        UserDataRequestDTO user = new UserDataRequestDTO();
        User userBD = new User();
        String expectedMessage = "Сохранение выполнено успешно";

        when(userRepository.findById(id)).thenReturn(Optional.of(userBD));

        // Act
        String message = userService.updateData(user, id);

        // Assert
        assertEquals(expectedMessage, message);
    }


    @Test
    void deleteUserByNickname() {
        // Arrange
        String nickname = "john";
        User user = new User();

        when(userRepository.getUserByNickname(nickname)).thenReturn(user);

        // Act
        String message = userService.deleteUserByNickname(nickname);

        // Assert
        assertEquals("Аккаунт удален", message);
        assertFalse(user.isEnabled());
        verify(userRepository).getUserByNickname(nickname);
        verify(userRepository).save(user);
    }

    @Test
    void deleteUser() {
        // Arrange
        long id = 1L;
        User user = new User();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // Act
        String message = userService.deleteUser(id);

        // Assert
        assertEquals("Аккаунт удален", message);
    }

    @Test
    void subscribe() {
        // Arrange
        long id = 1L;
        long subscriberId = 2L;
        User user = new User();
        User subscriber = new User();


        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));

        // Act
        String result = userService.subscribe(id, subscriberId);

        // Assert
        assertTrue(user.getSubscribers().contains(subscriber));
        assertEquals("Успешно", result);
    }

    @Test
    void unsubscribe() {
        // Arrange
        long id = 1L;
        long subscriberId = 2L;
        User user = new User();
        User subscriber = new User();
        user.getSubscribers().add(subscriber);
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findById(subscriberId)).thenReturn(Optional.of(subscriber));

        // Act
        String result = userService.unsubscribe(id, subscriberId);

        // Assert
        assertFalse(user.getSubscribers().contains(subscriber));
        assertEquals("Успешно", result);
    }

    @Test
    void getGroupList() {
        long userId = 1L;

        List<GroupResponseDTO> dtoList = new ArrayList<>();
        List<Group> groups = new ArrayList<>();

        when(userRepository.getGroupsByUserId(userId)).thenReturn(groups);
        when(groupMapper.toDto(groups)).thenReturn(dtoList);

        List<GroupResponseDTO> result = userService.getGroupList(userId);

        assertEquals(dtoList, result);
    }

    @Test
    void getSubscribersList() {
        long userId = 1L;

        List<UserResponseDTO> dtoList = new ArrayList<>();
        List<User> users = new ArrayList<>();

        when(userRepository.getSubscribersByUserId(userId)).thenReturn(users);
        when(userMapper.toDto(users)).thenReturn(dtoList);

        List<UserResponseDTO> result = userService.getSubscribersList(userId);

        assertEquals(dtoList, result);
    }

    @Test
    void getAlbumList() {
        long userId = 1L;

        List<AlbumResponseDTO> dtoList = new ArrayList<>();
        List<Album> albums = new ArrayList<>();

        when(userRepository.getAlbumByUserId(userId)).thenReturn(albums);
        when(albumMapper.toDto(albums)).thenReturn(dtoList);

        List<UserResponseDTO> result = userService.getSubscribersList(userId);

        assertEquals(dtoList, result);
    }

}