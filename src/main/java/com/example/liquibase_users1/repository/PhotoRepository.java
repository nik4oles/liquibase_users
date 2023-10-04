package com.example.liquibase_users1.repository;


import com.example.liquibase_users1.models.entity.Like;

import com.example.liquibase_users1.models.entity.Photo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Long> {
    Photo getPhotoById(long id);
    @Query("select p.likes from Photo p ")
    List<Like> getLikesPhotoById(long id);
    List<Photo> getPhotoByAlbum_Id(long id);

}
