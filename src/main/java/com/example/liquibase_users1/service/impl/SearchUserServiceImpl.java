package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.converter.user.UserMapper;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Location;
import com.example.liquibase_users1.models.entity.User;
import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.repository.UserRepository;
import com.example.liquibase_users1.service.SearchUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SearchUserServiceImpl implements SearchUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /** Поиск пользователя по значниям полей, в подписках другого полизователя и в группе */
    @Override
    public Page<UserResponseDTO> getAllUsersByParams(String nickname, String name, String lastname, int age, Location location, Gender gender, int pageNumber, int itemsOnPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        LocalDate dateOfBirth = LocalDate.now().minusYears(age);
        Page<User> page = userRepository.getAllUsersByParams(nickname, name, lastname, dateOfBirth, location, gender, pageable);
        return page.map(userMapper::toDto);
    }

    @Override
    public Page<UserResponseDTO> getUserByUserIdAndParams(Long id, String nickname, String name, String lastname, int age, Location location, Gender gender, int pageNumber, int itemsOnPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        LocalDate dateOfBirth = LocalDate.now().minusYears(age);
        Page<User> page = userRepository.getUserByUserIdAndParams(id, nickname, name, lastname, dateOfBirth, location, gender, pageable);
        return page.map(userMapper::toDto);
    }

    @Override
    public Page<UserResponseDTO> getAllUsersByGroupIdAndParams(Long id, String nickname, String name, String lastname, int age, Location location, Gender gender, int pageNumber, int itemsOnPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        LocalDate dateOfBirth = LocalDate.now().minusYears(age);
        Page<User> page = userRepository.getAllUsersByGroupIdAndParams(id, nickname, name, lastname, dateOfBirth, location, gender, pageable);
        return page.map(userMapper::toDto);
    }
}
