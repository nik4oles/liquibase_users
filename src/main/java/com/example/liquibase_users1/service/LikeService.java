package com.example.liquibase_users1.service;

import com.example.liquibase_users1.models.dto.request.LikeRequestDTO;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.entity.Like;

import java.util.List;

public interface LikeService {

    PhotoResponseDTO addLikePhoto(long userId, long photoId);

    PhotoResponseDTO removeLikePhoto(Long likeId, long photoId);

    void updateLike(LikeRequestDTO like, long id);

    List<Like> getLikesByPhoto(Long photoId);
}
