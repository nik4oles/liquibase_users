package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import com.example.liquibase_users1.converter.user.UserMapper;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Location;
import com.example.liquibase_users1.models.entity.User;
import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.repository.UserRepository;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SearchUserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SearchUserServiceImpl searchUserService;

    @Test
    public void testGetAllUsersByParams_ReturnsPageOfUserResponseDTO() {
        // Arrange
        String nickname = "test";
        String name = "John";
        String lastname = "Doe";
        int age = 30;
        Location location = new Location("City", "Country");
        Gender gender = Gender.MALE;
        int pageNumber = 0;
        int itemsOnPage = 10;

        List<User> users = new ArrayList<>();
        users.add(new User());
        Page<User> page = new PageImpl<>(users);

        when(userRepository.getAllUsersByParams(nickname, name, lastname, LocalDate.now().minusYears(age), location, gender, PageRequest.of(pageNumber, itemsOnPage))).thenReturn(page);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        // Act
        Page<UserResponseDTO> result = searchUserService.getAllUsersByParams(nickname, name, lastname, age, location, gender, pageNumber, itemsOnPage);

        // Assert
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(userResponseDTO, result.getContent().get(0));
    }

    @Test
    void getUserByUserIdAndParams() {
        // Arrange
        Long groupId = 1L;
        String nickname = "test";
        String name = "John";
        String lastname = "Doe";
        int age = 30;
        Location location = new Location("City", "Country");
        Gender gender = Gender.MALE;
        int pageNumber = 0;
        int itemsOnPage = 10;

        List<User> users = new ArrayList<>();
        users.add(new User());
        Page<User> page = new PageImpl<>(users);

        when(userRepository.getAllUsersByGroupIdAndParams(groupId, nickname, name, lastname, LocalDate.now().minusYears(age), location, gender, PageRequest.of(pageNumber, itemsOnPage))).thenReturn(page);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        // Act
        Page<UserResponseDTO> result = searchUserService.getAllUsersByGroupIdAndParams(groupId, nickname, name, lastname, age, location, gender, pageNumber, itemsOnPage);

        // Assert
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(userResponseDTO, result.getContent().get(0));
    }


    @Test
    void getAllUsersByGroupIdAndParams() {
        // Arrange
        Long groupId = 1L;
        String nickname = "test";
        String name = "John";
        String lastname = "Doe";
        int age = 30;
        Location location = new Location("City", "Country");
        Gender gender = Gender.MALE;
        int pageNumber = 0;
        int itemsOnPage = 10;

        List<User> users = new ArrayList<>();
        users.add(new User());
        Page<User> page = new PageImpl<>(users);

        when(userRepository.getAllUsersByGroupIdAndParams(groupId, nickname, name, lastname, LocalDate.now().minusYears(age), location, gender, PageRequest.of(pageNumber, itemsOnPage))).thenReturn(page);

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        when(userMapper.toDto(any(User.class))).thenReturn(userResponseDTO);

        // Act
        Page<UserResponseDTO> result = searchUserService.getAllUsersByGroupIdAndParams(groupId, nickname, name, lastname, age, location, gender, pageNumber, itemsOnPage);

        // Assert
        assertEquals(page.getTotalElements(), result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(userResponseDTO, result.getContent().get(0));
    }
}