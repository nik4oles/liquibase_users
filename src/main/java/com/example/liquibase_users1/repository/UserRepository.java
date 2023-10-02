package com.example.liquibase_users1.repository;


import com.example.liquibase_users1.models.entity.*;
import com.example.liquibase_users1.models.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    @EntityGraph(value = "userForSubscribeGraph")
    User getUserById(long id);

    User getUserByNickname(String nickName);

    @Query("select al from User u join u.albums al where u.id =:userId and al.id =:albumId")
    Album getAlbumByUserAndAlbumId(Long userId, Long albumId);

    @Query("select u.groups from User u")
    List<Group> getGroupsByUserId(Long userId);

    @Query("select u.subscribers from User u")
    List<User> getSubscribersByUserId(Long userId);

    @Query("select u.albums from User u")
    List<Album> getAlbumByUserId(Long userId);

    @Query("select s from User u join u.subscribers s where u.id = :id and " +
            "( :nickname is null or s.nickname = :nickname) and " +
            "( :name is null  or s.name = :name) and " +
            "( :lastname is null or s.lastname = :lastname) and " +
            "( :dateOfBirth is null or s.dateOfBirth = :dateOfBirth)  and " +
            "( :location is null or s.location = :location) and " +
            "( :gender is null or s.gender = :gender)")
    Page<User> getUserByUserIdAndParams(@Param("id") Long id,
                                        @Param("nickname") String nickname,
                                        @Param("name") String name,
                                        @Param("lastname") String lastname,
                                        @Param("dateOfBirth") LocalDate dateOfBirth,
                                        @Param("location") Location location,
                                        @Param("gender") Gender gender,
                                        Pageable pageable);

    @Query("select s from User u join u.subscribers s where " +
            "( :nickname is null or s.nickname = :nickname) and " +
            "( :name is null  or s.name = :name) and " +
            "( :lastname is null or s.lastname = :lastname) and " +
            "( :dateOfBirth is null or s.dateOfBirth = :dateOfBirth)  and " +
            "( :location is null or s.location = :location) and " +
            "( :gender is null or s.gender = :gender)")
    Page<User> getAllUsersByParams(@Param("nickname") String nickname,
                                   @Param("name") String name,
                                   @Param("lastname") String lastname,
                                   @Param("dateOfBirth") LocalDate dateOfBirth,
                                   @Param("location") Location location,
                                   @Param("gender") Gender gender, Pageable pageable);

    @Query("select u from Group g join g.subscribers u where g.id = :id and " +
            "( :nickname is null or u.nickname = :nickname) and " +
            "( :name is null  or u.name = :name) and " +
            "( :lastname is null or u.lastname = :lastname) and " +
            "( :dateOfBirth is null or u.dateOfBirth = :dateOfBirth)  and " +
            "( :location is null or u.location = :location) and " +
            "( :gender is null or u.gender = :gender)")
    Page<User> getAllUsersByGroupIdAndParams(@Param("id") Long id,
                                             @Param("nickname") String nickname,
                                             @Param("name") String name,
                                             @Param("lastname") String lastname,
                                             @Param("dateOfBirth") LocalDate dateOfBirth,
                                             @Param("location") Location location,
                                             @Param("gender") Gender gender,
                                             Pageable pageable);


    void deleteByNickname(String nickname);
}
