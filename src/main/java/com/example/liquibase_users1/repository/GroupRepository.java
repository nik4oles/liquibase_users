package com.example.liquibase_users1.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.liquibase_users1.models.entity.*;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<Group, Long> {

    @Query("select al from User u join u.albums al where u.id =:userId and al.id =:albumId")
    Album getAlbumByGroupAndAlbumId(Long userId, Long albumId);

    @Query("select g.subscribers from Group g")
    List<User> getSubscribersByGroupId(Long userId);

    @Query("select g.albums from Group g")
    List<Album> getAlbumByGroupId(Long userId);

    //    @Query("select g from Group g where g.name = :name and g.description like concat('%', :description, '%') ")
    @Query("select g from Group g where g.name = :name and g.description = :description ")
    Page<Group> getAllGroupsByParams(@Param("name") String name,
                                     @Param("description") String description, Pageable pageable);
//    @Query("select g from User u join u.groups g where g.name = :name and g.description like concat('%', :description, '%') ")
    @Query("select g from User u join u.groups g where u.id = :id and g.name = :name and g.description = :description ")
    Page<Group> getGroupsByUserIdAndParams(@Param("id") Long id,
                                           @Param("name") String name,
                                           @Param("description") String description, Pageable pageable);


}
