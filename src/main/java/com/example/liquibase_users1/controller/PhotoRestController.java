package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.service.AlbumService;
import com.example.liquibase_users1.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/photos/")
public class PhotoRestController {
    private final AlbumService albumService;
    private final LikeService likeService;

    @Operation(summary = "Получение фото")
    @GetMapping("{id}")
    ResponseEntity<PhotoResponseDTO> getPhoto(@PathVariable long id) {
        return new ResponseEntity<>(albumService.getPhotoResponseDTO(id), HttpStatus.OK);
    }

    @Operation(summary = "Удаление фото")
    @DeleteMapping("{id}")
    ResponseEntity<String> deletePhoto(@PathVariable long id) {
        return new ResponseEntity<>(albumService.deletePhoto(id), HttpStatus.OK);
    }

    @Operation(summary = "Добавление лайка")
    @PostMapping("{photoId}/likes/")
    ResponseEntity<PhotoResponseDTO> addLikePhoto(@PathVariable long photoId, @RequestParam long userId) {
        return new ResponseEntity<>(likeService.addLikePhoto(userId, photoId), HttpStatus.OK);
    }

    @Operation(summary = "Удаление лайка")
    @DeleteMapping("{photoId}/likes/{likeId}")
    ResponseEntity<PhotoResponseDTO> deleteLike(@PathVariable long likeId, @PathVariable long photoId) {
        return new ResponseEntity<>(likeService.removeLikePhoto(photoId, likeId), HttpStatus.OK);
    }
}
