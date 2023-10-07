package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.request.AlbumRequestDTO;
import com.example.liquibase_users1.models.dto.request.GroupRegistrationRequestDTO;
import com.example.liquibase_users1.models.dto.request.GroupRequestDTO;
import com.example.liquibase_users1.models.dto.response.AlbumResponseDTO;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.service.AlbumService;
import com.example.liquibase_users1.service.GroupService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/")
public class GroupRestController {
    private final GroupService groupService;
    private final AlbumService albumService;

    @Operation(summary = "Удаление группы")
    @PostMapping()
    ResponseEntity<GroupResponseDTO> createGroup(@RequestBody GroupRegistrationRequestDTO groupRequestDTO) {
        return new ResponseEntity<>(groupService.createGroup(groupRequestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Получение группы")
    @GetMapping("/{id}")
    ResponseEntity<GroupResponseDTO> getGroup(@PathVariable long id) {
        return new ResponseEntity<>(groupService.getGroupResponseDTO(id), HttpStatus.OK);
    }

    @Operation(summary = "Обновление группы")
    @PutMapping("/{id}")
    ResponseEntity<GroupResponseDTO> updateGroup(@RequestBody GroupRequestDTO groupRequestDTO, @PathVariable long id) {
        return new ResponseEntity<>(groupService.updateGroup(groupRequestDTO, id), HttpStatus.OK);
    }

    @Operation(summary = "Удаление группы")
    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteGroup(@PathVariable long id) {
        return new ResponseEntity<>(groupService.deleteGroup(id), HttpStatus.OK);
    }

    @Operation(summary = "Подписаться на группу")
    @PostMapping("/{id}/subscribe/{subscriberId}")
    ResponseEntity<String> subscribe(@PathVariable long id, @PathVariable long subscriberId) {

        return new ResponseEntity<>(groupService.subscribe(id, subscriberId), HttpStatus.OK);
    }

    @Operation(summary = "Отписаться от группы")
    @PostMapping("/{id}/unsubscribe/{subscriberId}")
    ResponseEntity<String> unsubscribe(@PathVariable long id, @PathVariable long subscriberId) {
        groupService.unsubscribe(id, subscriberId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Создание альбома")
    @PostMapping("/{id}/albums")
    ResponseEntity<AlbumResponseDTO> createAlbum(@PathVariable long id, @RequestBody AlbumRequestDTO albumRequestDTO) {
        return new ResponseEntity<>(albumService.createAlbumGroup(id, albumRequestDTO), HttpStatus.OK);

    }

}
