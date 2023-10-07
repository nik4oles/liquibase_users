package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.request.PhotoRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.service.AlbumService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/albums/")
@RequiredArgsConstructor
public class AlbumRestController {
    private final AlbumService albumService;


    @Operation(summary = "Добавление альбома")
    @GetMapping("{id}")
    ResponseEntity<AlbumResponseDTO> getAlbum(@PathVariable long id) {
        return new ResponseEntity<>(albumService.getAlbumResponseDTO(id), HttpStatus.OK);
    }

    @Operation(summary = "Обновление альбома")
    @PutMapping("{id}")
    ResponseEntity<AlbumResponseDTO> updateAlbum(@RequestBody AlbumRequestDTO albumRequestDTO, @PathVariable long id) {
        return new ResponseEntity<>(albumService.updateAlbum(id, albumRequestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Удаление альбома")
    @DeleteMapping("{id}")
    ResponseEntity<String> deleteAlbum(@PathVariable long id) {
        return new ResponseEntity<>(albumService.deleteAlbum(id), HttpStatus.OK);
    }

    @Operation(summary = "Добавление фото в альбом")
    @PostMapping("/{id}/photos")
    ResponseEntity<AlbumResponseDTO> addAlbumPhoto(@PathVariable long id, @RequestBody PhotoRequestDTO photo) {
        return new ResponseEntity<>(albumService.addPhoto(id, photo), HttpStatus.OK);
    }
}
