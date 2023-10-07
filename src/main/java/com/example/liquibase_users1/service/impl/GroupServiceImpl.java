package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.converter.album.AlbumMapper;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.converter.group.GroupRegistrationMapper;
import com.example.liquibase_users1.converter.user.UserMapper;
import com.example.liquibase_users1.models.dto.request.GroupRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.GroupRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Group;
import com.example.liquibase_users1.models.entity.User;
import com.example.liquibase_users1.repository.GroupRepository;
import com.example.liquibase_users1.repository.UserRepository;
import com.example.liquibase_users1.service.GroupService;
import com.example.liquibase_users1.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@Component
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final UserService userService;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final GroupMapper groupMapper;
    private final AlbumMapper albumMapper;
    private final GroupRegistrationMapper groupRegistrationMapper;
    @Override
    public GroupResponseDTO createGroup(GroupRegistrationRequestDTO groupDTO) {
        Group group = groupRegistrationMapper.toEntity(groupDTO);
        group.setOwner(userRepository.getUserById(groupDTO.getOwnerId()));
        return groupMapper.toDto(groupRepository.save(group));
    }

    /** Получение */

    public Group getGroup(long id) {
        return groupRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
    @Override
    public GroupResponseDTO getGroupResponseDTO(long id) {
        return groupMapper.toDto(groupRepository.getGroupById(id));
    }


    /** Обновление информации */
    @Override
    public GroupResponseDTO updateGroup(GroupRequestDTO group, long id) {

        Group groupBD = groupRepository.getGroupById(id);

        groupBD.setName(group.getName());
        groupBD.setDescription(group.getDescription());

        return groupMapper.toDto(groupRepository.save(groupBD));
    }

    @Override
    public void addStory(long id, String story) {
        Group group = getGroup(id);

        group.setShortStoryAboutGroup(story);
        groupRepository.save(group);

    }
    @Override
    public void closeGroup(long id, boolean b) {
        Group group = getGroup(id);

        if (group.isPrivate() == b) return;
        group.setPrivate(b);
        groupRepository.save(group);

    }
    /** Удаление */
    @Override
    public String deleteGroup(long id) {
        Group group = getGroup(id);
        group.setEnabled(false);
        groupRepository.save(group);
        return String.format("Группа удалена");
    }

    /** Подписки */
    @Override
    public String subscribe(long id, long subscriberId) {
        Group group = groupRepository.getGroupById(id);
        User subscriber = userService.getUser(subscriberId);

        for (User user: group.getSubscribers()) {
            if(user.getId() == subscriberId) return "Вы уже подписаны ";
        }

        group.getSubscribers().add(subscriber);
        groupRepository.save(group);
        return "Вы подписались на " + group.getName();
    }
    @Override
    public String unsubscribe(long id, long subscriberId) {
        Group group = groupRepository.getGroupById(id);
        User subscriber = userService.getUser(subscriberId);

        for (User user: group.getSubscribers()) {
            if(user.getId() == subscriberId) {
                return "Вы уже отписались от " + group.getName();
            }
        }

        group.getSubscribers().remove(subscriber);
        groupRepository.save(group);
        return "Вы отписались от" + group.getName();
    }

    /** Получение списков пользователей и альбомов */
    @Override
    public List<UserResponseDTO> getSubscribersList(Long userId) {
        return  userMapper.toDto(groupRepository.getSubscribersByGroupId(userId));
    }
    @Override
    public List<AlbumResponseDTO> getAlbumList(Long groupId) {
        return albumMapper.toDto(groupRepository.getAlbumByGroupId(groupId));
    }
}
