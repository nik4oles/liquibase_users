package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.models.entity.Location;
import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.service.SearchUserService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary ="поиск пользователя по параметрам")
    @GetMapping("/users/page/{pageNumber}")
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

    @Operation(summary ="поиск пользователя среди подписок пользователя по параметрам")
    @GetMapping("/users/{id}/page/{pageNumber}")
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

    @Operation(summary ="поиск пользователя среди подписок на группы по параметрам")
    @GetMapping("/users/groups/{id}/page/{pageNumber}")
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
