package com.example.liquibase_users1.service.impl;


import com.example.liquibase_users1.converter.album.AlbumMapper;
import com.example.liquibase_users1.converter.user.UserMapper;
import com.example.liquibase_users1.converter.user.UserRegistrationMapper;
import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.models.dto.request.UserDataRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.*;
import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.models.enums.Status;
import com.example.liquibase_users1.repository.AlbumRepository;
import com.example.liquibase_users1.repository.RoleRepository;
import com.example.liquibase_users1.repository.UserRepository;
import com.example.liquibase_users1.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AlbumRepository albumRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final UserRegistrationMapper userRegistrationMapper;
    private final GroupMapper groupMapper;
    private final AlbumMapper albumMapper;

    /** Сохранение пользователя */

    @Override
    public UserResponseDTO createUser(UserRegistrationRequestDTO userDTO) {
        User userFromBD = userRepository.getUserByNickname(userDTO.getNickname());
        if(userFromBD != null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        User user = userRegistrationMapper.toEntity(userDTO);

        user.createNewUser();
        Album album = user.getMainAlbum();
        albumRepository.save(album);
        User savedUser = userRepository.save(user);

        UserResponseDTO userResponseDTO = userMapper.toDto(savedUser) ;
        return userResponseDTO;
    }

    /** Получение пользователя */
    @Override
    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    public UserResponseDTO getUserResponseDTO(long id) {
        return userMapper.toDto(getUser(id));
    }
    @Override
    public UserResponseDTO getUserResponseDTOByNickname(String nickname) {
        User user = userRepository.getUserByNickname(nickname);

        if( user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return userMapper.toDto(user);
    }


    /** Обновление информации */
    @Override
    public UserResponseDTO updateUser(UserRequestDTO userDTO, long id) {

        User userBD = getUser(id);
        userBD.setName(userDTO.getName());
        userBD.setLocation(new Location(userDTO.getLocation().getCountry(), userDTO.getLocation().getCity()));
        userBD.setGender(Gender.valueOf(userDTO.getGender()));
        userBD.setDateOfBirth(userDTO.getDateOfBirth());
        userBD.setLastname(userDTO.getLastname());

        userBD = userRepository.save(userBD);

        return userMapper.toDto(userBD);
    }
    @Override
    public String updateData(UserDataRequestDTO user, long id) {

        User userBD = getUser(id);
        userBD.setPassword(user.getPassword());
        userBD.setNickname(user.getNickname());
        userBD.setEmail(user.getEmail());
        userBD.setNumber(user.getNumber());

        return String.format("Сохранение выполнено успешно");
    }
    @Override
    public void addStoryAboutUser(long id, String story) {
        User user = getUser(id);
        user.setShortStoryAboutUser(story);
        userRepository.save(user);
    }
    @Override
    public void closeAccount(long id, boolean b) {
        User user = getUser(id);
        if (user.isPrivate() == b) return;
        user.setPrivate(b);
        userRepository.save(user);
    }

    @Override
    public void status(long id, String status) {
        User user = getUser(id);
        user.setStatus(Status.valueOf(status));
        userRepository.save(user);
    }

    /** Удаление пользователя */
    @Override
    @Transactional
    public String deleteUserByNickname(String nickname) {
        User user = userRepository.getUserByNickname(nickname);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        user.setEnabled(false);
        userRepository.save(user);

        return String.format("Аккаунт удален");
    }
    @Override
    public String deleteUser(long id) {
        User user = getUser(id);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        user.setEnabled(false);
        userRepository.save(user);

        return String.format("Аккаунт удален");
    }

    /** Подписки */
    @Override
    @Transactional
    public String subscribe(long id, long subscriberId) {
        User user = getUser(id);
        User subscriber = getUser(subscriberId);


        if (user.getSubscribers().contains(subscriber)) {
            throw new RuntimeException(String.format("Пользователь  с %s уже подписан на пользователя  с %s", id, subscriberId));
        }

        user.getSubscribers().add(subscriber);
        userRepository.save(user);
        return "Успешно";
    }
    @Override
    public String unsubscribe(long id, long subscriberId) {
        User user = userRepository.getUserById(id);
        User subscriber = getUser(subscriberId);

        if (!user.getSubscribers().contains(subscriber)) {
            return String.format("Пользователь с id = %s не подписан на пользователя с id =%s", id, subscriberId);
        }

        user.getSubscribers().remove(subscriber);
        System.out.println(user.getSubscribers().size());
        userRepository.save(user);
        return "Успешно";
    }

    /** Получение списков групп, подписчиков и альбов пользователя */
    @Override
    public List<GroupResponseDTO> getGroupList(Long userId) {
        List<GroupResponseDTO> dtoList = groupMapper.toDto(userRepository.getGroupsByUserId(userId));
        return dtoList;
    }
    @Override
    public List<UserResponseDTO> getSubscribersList(Long userId) {
        List<UserResponseDTO> dtoList = userMapper.toDto(userRepository.getSubscribersByUserId(userId));
        return dtoList;
    }
    @Override
    public List<AlbumResponseDTO> getAlbumList(Long userId) {
        List<AlbumResponseDTO> dtoList = albumMapper.toDto(userRepository.getAlbumByUserId(userId));
        return dtoList;
    }


    /** Роли пользователя */
    @Override
    public void deleteRoleFromUser(Long userId, Long roleId) {
        User user = getUser(userId);
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.getRoles().remove(role);
    }

    @Override
    public void addRoleToUser(Long userId, Long roleId) {
        User user = getUser(userId);
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.getRoles().add(role);
    }

}
