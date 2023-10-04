package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.service.SearchGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SearchGroupRestController {
    private final SearchGroupService searchGroupService;

    @GetMapping("/search/groups")
    ResponseEntity<Page<GroupResponseDTO>> getAllGroupsByParams(@RequestParam String name,
                                                                @PathVariable int pageNumber,
                                                                @RequestParam(defaultValue = "10") int itemsOnPage,
                                                                @RequestParam String description) {
        return new ResponseEntity<>(searchGroupService.getAllGroupsByParams(name, description, pageNumber, itemsOnPage), HttpStatus.OK);
    }

    @GetMapping("/search/groups/users/{id}")
    ResponseEntity<Page<GroupResponseDTO>> getGroupsByUserIdAndParams(@PathVariable Long id,
                                                                      @PathVariable int pageNumber,
                                                                      @RequestParam(defaultValue = "10") int itemsOnPage,
                                                                      @RequestParam String name,
                                                                      @RequestParam String description) {
        return new ResponseEntity<>(searchGroupService.getGroupsByUserIdAndParams(id, name, description, pageNumber, itemsOnPage), HttpStatus.OK);
    }
}
