package com.example.liquibase_users1.service;

import com.example.liquibase_users1.models.dto.request.GroupRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.GroupRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Group;

import java.util.List;

public interface GroupService {

    GroupResponseDTO createGroup(GroupRegistrationRequestDTO groupDTO);

    public Group getGroup(long id);

    GroupResponseDTO getGroupResponseDTO(long id);

    GroupResponseDTO updateGroup(GroupRequestDTO group, long id);

    void addStory(long id, String story);

    void closeGroup(long id, boolean b);

    String deleteGroup(long id);

    String subscribe(long id, long subscriberId);

    String unsubscribe(long id, long subscriberId);

    List<UserResponseDTO> getSubscribersList(Long userId);

    List<AlbumResponseDTO> getAlbumList(Long groupId);
}
