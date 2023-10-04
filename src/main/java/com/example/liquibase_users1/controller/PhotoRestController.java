package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.entity.Like;
import com.example.liquibase_users1.service.AlbumService;
import com.example.liquibase_users1.service.LikeService;
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

    @GetMapping("{id}")
    ResponseEntity<PhotoResponseDTO> getPhoto(@PathVariable long id) {
        return new ResponseEntity<>(albumService.getPhotoResponseDTO(id), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    ResponseEntity<String> deletePhoto(@PathVariable long id) {
        return new ResponseEntity<>(albumService.deletePhoto(id), HttpStatus.OK);
    }

    @PostMapping("{photoId}/likes/")
    ResponseEntity<PhotoResponseDTO> addLikePhoto(@PathVariable long photoId, @RequestBody Like like) {
        return new ResponseEntity<>(likeService.addLikePhoto(like, photoId), HttpStatus.OK);
    }

    @DeleteMapping("{photoId}/likes/{likeId}")
    ResponseEntity<PhotoResponseDTO> deleteLike(@PathVariable long likeId, @PathVariable long photoId) {
        return new ResponseEntity<>(likeService.removeLikePhoto(photoId, likeId), HttpStatus.OK);
    }
}
