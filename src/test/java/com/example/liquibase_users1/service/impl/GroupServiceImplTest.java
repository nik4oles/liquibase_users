package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import com.example.liquibase_users1.converter.album.AlbumMapper;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.converter.group.GroupRegistrationMapper;
import com.example.liquibase_users1.converter.user.UserMapper;
import com.example.liquibase_users1.models.dto.request.GroupRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.GroupRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Album;
import com.example.liquibase_users1.models.entity.Group;
import com.example.liquibase_users1.models.entity.User;
import com.example.liquibase_users1.repository.GroupRepository;
import com.example.liquibase_users1.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class GroupServiceImplTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private GroupMapper groupMapper;
    @Mock
    private AlbumMapper albumMapper;
    @Mock
    private GroupRegistrationMapper groupRegistrationMapper;

    @InjectMocks
    private GroupServiceImpl groupService;


    @Test
    void createGroup() {
        // Arrange
        GroupRegistrationRequestDTO groupDTO = new GroupRegistrationRequestDTO();
        Group group = new Group();
        when(groupRegistrationMapper.toEntity(groupDTO)).thenReturn(group);
        when(userRepository.getUserById(1L)).thenReturn(new User());
        when(groupRepository.save(group)).thenReturn(group);
        when(groupMapper.toDto(group)).thenReturn(new GroupResponseDTO());

        // Act
        GroupResponseDTO result = groupService.createGroup(groupDTO);

        // Assert
        assertNotNull(result);
        assertEquals(GroupResponseDTO.class, result.getClass());
    }

    @Test
    void getGroup() {
        // Arrange
        long id = 1L;
        Group group = new Group();
        when(groupRepository.findById(id)).thenReturn(java.util.Optional.of(group));

        // Act
        Group result = groupService.getGroup(id);

        // Assert
        assertNotNull(result);
        assertEquals(Group.class, result.getClass());
    }

    @Test
    void getGroupResponseDTO() {
        // Arrange
        long id = 1L;
        Group group = new Group();
        when(groupRepository.findById(id)).thenReturn(java.util.Optional.of(group));
        when(groupMapper.toDto(group)).thenReturn(new GroupResponseDTO());

        // Act
        GroupResponseDTO result = groupService.getGroupResponseDTO(id);

        // Assert
        assertNotNull(result);
        assertEquals(GroupResponseDTO.class, result.getClass());
    }

    @Test
    void updateGroup() {
        // Arrange
        long id = 1L;
        GroupRequestDTO groupDTO = new GroupRequestDTO();
        Group group = new Group();
        when(groupRepository.findById(id)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);
        when(groupMapper.toDto(group)).thenReturn(new GroupResponseDTO());

        // Act
        GroupResponseDTO result = groupService.updateGroup(groupDTO, id);

        // Assert
        assertNotNull(result);
        assertEquals(GroupResponseDTO.class, result.getClass());
    }




    @Test
    void deleteGroup() {
        // Arrange
        long id = 1L;
        Group group = new Group();
        when(groupRepository.findById(id)).thenReturn(Optional.of(group));
        when(groupRepository.save(group)).thenReturn(group);

        // Act
        String result = groupService.deleteGroup(id);

        // Assert
        assertEquals("Группа удалена", result);
        assertFalse(group.isEnabled());
    }

    @Test
    void getSubscribersList() {
        // Arrange
        long groupId = 1L;
        List<User> subscribers = new ArrayList<>();
        List<UserResponseDTO> userResponseDTOS = new ArrayList<>();
        subscribers.add(new User());
        userResponseDTOS.add(new UserResponseDTO());
        when(groupRepository.getSubscribersByGroupId(groupId)).thenReturn(subscribers);
        when(userMapper.toDto(subscribers)).thenReturn(userResponseDTOS);

        // Act
        List<UserResponseDTO> result = groupService.getSubscribersList(groupId);

        // Assert
        assertEquals(userResponseDTOS, result);
    }

    @Test
    void getAlbumList() {
        // Arrange
        long groupId = 1L;
        List<Album> albums = new ArrayList<>();
        List<AlbumResponseDTO> albumResponseDTOS = new ArrayList<>();
        albums.add(new Album());
        albumResponseDTOS.add(new AlbumResponseDTO());
        when(groupRepository.getAlbumByGroupId(groupId)).thenReturn(albums);
        when(albumMapper.toDto(albums)).thenReturn(albumResponseDTOS);

        // Act
        List<AlbumResponseDTO> result = groupService.getAlbumList(groupId);

        // Assert
        assertEquals(albumResponseDTOS, result);
    }
}