package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.converter.group.GroupMapper;
import com.example.liquibase_users1.models.dto.response.GroupResponseDTO;
import com.example.liquibase_users1.models.entity.Group;
import com.example.liquibase_users1.repository.GroupRepository;
import com.example.liquibase_users1.service.SearchGroupService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SearchGroupServiceImpl implements SearchGroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    /** Поиск групп по значниям полей и среди групп какого-то полизователя */
    @Override
    @Transactional
    public Page<GroupResponseDTO> getAllGroupsByParams(String name, String description, int pageNumber, int itemsOnPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        Page<Group> page =  groupRepository.getAllGroupsByParams(name, description, pageable);
        return page.map(groupMapper::toDto);
    }

    @Override
    @Transactional
    public Page<GroupResponseDTO> getGroupsByUserIdAndParams(Long id, String name, String description, int pageNumber, int itemsOnPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsOnPage);
        Page<Group> page =  groupRepository.getGroupsByUserIdAndParams(id, name, description, pageable);
        return page.map(groupMapper::toDto);
    }
}
