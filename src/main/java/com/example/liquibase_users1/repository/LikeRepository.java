package com.example.liquibase_users1.repository;

import com.example.liquibase_users1.models.entity.Like;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long> {
    Like getLikeById(Long likeId);

}
