package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminRestController {
    private final UserService userService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> addRoleToUser(@RequestParam Long roleId, @PathVariable("id") Long userId) {
        userService.addRoleToUser(userId, roleId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoleFromUser(@RequestParam Long roleId, @PathVariable("id") Long id) {
        userService.deleteRoleFromUser(id, roleId);
        return ResponseEntity.ok().build();
    }

}
