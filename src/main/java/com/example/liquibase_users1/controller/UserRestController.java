package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.UserRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.UserResponseDTO;
import com.example.liquibase_users1.service.AlbumService;
import com.example.liquibase_users1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {
    private final UserService userService;
    private final AlbumService albumService;

    @PostMapping()
    ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRegistrationRequestDTO user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserResponseDTO> getUserResponseDTO(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUserResponseDTO(id), HttpStatus.OK);

    }

    @GetMapping("/nicknames/{nickname}")
    ResponseEntity<UserResponseDTO> getUserResponseDTOnByNickname(@PathVariable String nickname) {
        return new ResponseEntity<>(userService.getUserResponseDTOByNickname(nickname), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserRequestDTO userRequestDTO, @PathVariable long id) {
        return new ResponseEntity<>(userService.updateUser(userRequestDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteUser(@PathVariable long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

    @DeleteMapping("nicknames/{nickname}")
    ResponseEntity<String> deleteUserByNickname(@PathVariable String nickname) {
        return new ResponseEntity<>(userService.deleteUserByNickname(nickname), HttpStatus.OK);
    }

    @PostMapping("/{id}/subscribe/{subscriberId}")
    ResponseEntity<String> subscribe(@PathVariable long id, @PathVariable long subscriberId) {
        return new ResponseEntity<>(userService.subscribe(id, subscriberId), HttpStatus.OK);
    }

    @PostMapping("/{id}/unsubscribe/{subscriberId}")
    ResponseEntity<String> unsubscribe(@PathVariable long id, @PathVariable long subscriberId) {
        return new ResponseEntity<>(userService.unsubscribe(id, subscriberId), HttpStatus.OK);
    }

    @PostMapping("/{id}/albums/{albumsId}")
    ResponseEntity<AlbumResponseDTO> createAlbum(@PathVariable long albumsId, @RequestBody AlbumRequestDTO albumRequestDTO) {
        return new ResponseEntity<>(albumService.createAlbumUser(albumsId, albumRequestDTO), HttpStatus.OK);
        //TODO получать дто
        //TODO вернуть дто
    }
}
