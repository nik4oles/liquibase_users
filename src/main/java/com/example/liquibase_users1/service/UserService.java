package com.example.liquibase_users1.service;

import com.example.liquibase_users1.models.dto.request.UserDataRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.User;

import java.util.List;

public interface UserService {


    UserResponseDTO createUser(UserRegistrationRequestDTO userDTO);

    User getUser(long id);

    UserResponseDTO getUserResponseDTO(long id);

    UserResponseDTO getUserResponseDTOByNickname(String nickname);

    UserResponseDTO updateUser(UserRequestDTO userDTO, long id);

    String updateData(UserDataRequestDTO user, long id);

    void addStoryAboutUser(long id, String story);

    void closeAccount(long id, boolean b);

    void status(long id, String status);
    String deleteUserByNickname(String nickname);

    String deleteUser(long id);

    String subscribe(long id, long subscriberId);

    String unsubscribe(long id, long subscriberId);

    List<GroupResponseDTO> getGroupList(Long userId);

    List<UserResponseDTO> getSubscribersList(Long userId);

    List<AlbumResponseDTO> getAlbumList(Long userId);

    void deleteRoleFromUser(Long userId, Long roleId);

    void addRoleToUser(Long userId, Long roleId);
}
