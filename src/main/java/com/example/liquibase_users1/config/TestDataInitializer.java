package com.example.liquibase_users1.config;

import com.example.liquibase_users1.models.dto.request.*;
import com.example.liquibase_users1.service.AlbumService;
import com.example.liquibase_users1.service.GroupService;
import com.example.liquibase_users1.service.LikeService;
import com.example.liquibase_users1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.net.URI;


@Component
@ConditionalOnExpression("${run.init:false}")
@RequiredArgsConstructor
public class TestDataInitializer {

    private final UserService userService;
    private final AlbumService albumService;
    private final GroupService groupService;
    private final LikeService likeService;

    @EventListener(ApplicationReadyEvent.class)
    @Order(1)
    public void initUsers() {
        for (int i = 1; i <= 15; i++) {
            UserRegistrationRequestDTO user = new UserRegistrationRequestDTO();
            user.setName("name" + i);
            user.setNickname("@Nick" + i);
            user.setLastname("lname" + i);
            user.setEmail("user" + i + "@skillbox.ru");
            user.setPassword(String.valueOf(i).repeat(4));
             userService.createUser(user);

        }
        for (int i = 1; i < 10; i++) {
            userService.subscribe(i, ++i);
            initAlbums(i);
        }
    }


    public void initAlbums(long l) {
        for (int i = 1; i <= 10; i++) {

            AlbumRequestDTO albumRequestDTO = new AlbumRequestDTO();
            albumRequestDTO.setDescription( l + "  opis" + i * 111);
            albumRequestDTO.setName( l + " Name" + i * 10);

            albumService.createAlbumUser(l, albumRequestDTO);
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(2)
    public void initGroups() {
        for (int i = 1; i <= 10; i++) {
            GroupRegistrationRequestDTO group = new GroupRegistrationRequestDTO();
            group.setName("name" + i);
            group.setDescription("lname" + i);
            group.setOwnerId(i);
            groupService.createGroup(group);


        }
        for (int i = 1; i < 10; i++) {
            groupService.subscribe(i, i);
            groupService.subscribe(i, ++i);
            initAlbumsGroup(i);
        }
    }


    public void initAlbumsGroup(long l) {
        for (int i = 1; i <= 10; i++) {

            AlbumRequestDTO album = new AlbumRequestDTO();

            album.setDescription( l + "  opis" + i * 111);
            album.setName( l + " Name" + i * 10);
            albumService.createAlbumGroup(l, album);

        }
    }

    @EventListener(ApplicationReadyEvent.class)
    @Order(3)
    public void initPhoto() {
        for(int i = 1; i < 100; i++) {
            PhotoRequestDTO photo = new PhotoRequestDTO();
            photo.setUri(URI.create("file:///Users/Username/Pictures/photo"+ i +".jpg"));
            photo.setTitle("Opisanie_"+ "Photo" + i);
            albumService.addPhoto(i%10 + 1, photo);
        }

    }
    @EventListener(ApplicationReadyEvent.class)
    @Order(4)
    public void initLikes() {

        for(int i = 1; i < 10; i++) {
            likeService.addLikePhoto(i, 1);
        }

    }
}
