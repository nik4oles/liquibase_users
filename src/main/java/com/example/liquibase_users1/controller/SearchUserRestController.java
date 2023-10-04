package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Location;
import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.service.SearchUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchUserRestController {
    private final SearchUserService searchUserService;

    @GetMapping("/users")
    ResponseEntity<Page<UserResponseDTO>> getAllUsersByParams(
            @RequestParam String nickname,
            @RequestParam String name,
            @RequestParam String lastname,
            @RequestParam int age,
            @RequestParam Location location,
            @RequestParam Gender gender,
            @PathVariable int pageNumber,
            @RequestParam(defaultValue = "10") int itemsOnPage) {
        return new ResponseEntity<>(searchUserService.getAllUsersByParams(nickname, name, lastname, age, location, gender, pageNumber, itemsOnPage), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<Page<UserResponseDTO>> getUserByUserIdAndParams(@PathVariable Long id,
                                                                   @PathVariable int pageNumber,
                                                                   @RequestParam(defaultValue = "10") int itemsOnPage,
                                                                   @RequestParam String nickname,
                                                                   @RequestParam String name,
                                                                   @RequestParam String lastname,
                                                                   @RequestParam int age,
                                                                   @RequestParam Location location,
                                                                   @RequestParam Gender gender) {
        return new ResponseEntity<>(searchUserService.getUserByUserIdAndParams(id, nickname, name, lastname, age, location, gender, pageNumber, itemsOnPage), HttpStatus.OK);
    }

    @GetMapping("/users/groups/{id}")
    ResponseEntity<Page<UserResponseDTO>> getAllUsersByGroupIdAndParams(@PathVariable Long id,
                                                                        @RequestParam String nickname,
                                                                        @RequestParam String name,
                                                                        @RequestParam String lastname,
                                                                        @RequestParam int age,
                                                                        @RequestParam Location location,
                                                                        @RequestParam Gender gender,
                                                                        @PathVariable int pageNumber,
                                                                        @RequestParam(defaultValue = "10") int itemsOnPage) {
        return new ResponseEntity<>(searchUserService.getAllUsersByGroupIdAndParams(id, nickname, name, lastname, age, location, gender, pageNumber, itemsOnPage), HttpStatus.OK);
    }

}
