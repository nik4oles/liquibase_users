package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.entity.Group;
import com.example.liquibase_users1.repository.GroupRepository;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SearchGroupServiceImplTest {
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private SearchGroupServiceImpl searchGroupService;


    @Test
    void getAllGroupsByParams() {
        // Arrange
        String name = "group";
        String description = "test";
        int pageNumber = 0;
        int itemsOnPage = 10;
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        List<Group> groups = new ArrayList<>();
        groups.add(new Group());
        groups.add(new Group());
        Page<Group> page = new PageImpl<>(groups);
        when(groupRepository.getAllGroupsByParams(name, description, pageable)).thenReturn(page);
        when(groupMapper.toDto(any(Group.class))).thenReturn(new GroupResponseDTO());

        // Act
        Page<GroupResponseDTO> result = searchGroupService.getAllGroupsByParams(name, description, pageNumber, itemsOnPage);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(groupRepository, times(1)).getAllGroupsByParams(name, description, pageable);
        verify(groupMapper, times(2)).toDto(any(Group.class));
    }

    @Test
    void getGroupsByUserIdAndParams() {

        // Arrange
        Long userId = 1L;
        String name = "group";
        String description = "test";
        int pageNumber = 0;
        int itemsOnPage = 10;
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        List<Group> groups = new ArrayList<>();
        groups.add(new Group());
        groups.add(new Group());
        Page<Group> page = new PageImpl<>(groups);
        when(groupRepository.getGroupsByUserIdAndParams(userId, name, description, pageable)).thenReturn(page);
        when(groupMapper.toDto(any(Group.class))).thenReturn(new GroupResponseDTO());

        // Act
        Page<GroupResponseDTO> result = searchGroupService.getGroupsByUserIdAndParams(userId, name, description, pageNumber, itemsOnPage);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        verify(groupRepository, times(1)).getGroupsByUserIdAndParams(userId, name, description, pageable);
        verify(groupMapper, times(2)).toDto(any(Group.class));
    }
}