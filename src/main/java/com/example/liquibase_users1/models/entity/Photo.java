package com.example.liquibase_users1.models.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "photos")
public class Photo { // фотография пользователя
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    private URI uri;
    private String title; // описание фото, которое придумывает владелец фото
    @OneToMany
    @JoinTable(name = "photos_likes",
            joinColumns = @JoinColumn(name = "photos_id"),
            inverseJoinColumns = @JoinColumn(name = "likes_id")
    )
    private List<Like> likes; //количиство лайков на фото
    @ManyToMany
    @JoinTable(name = "photos_tag_users",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "photos_id")
    )
    private List<User> tagUser; //отмеченные пользователи на фото
    private LocalDate publicationDate; //день публикации фото

    public Photo() {
    }
    public void createNewPhoto() {
        publicationDate = LocalDate.now();
        likes = new ArrayList<>();
    }
    public void addTagUser(User user) {
        if (!tagUser.contains(user)) tagUser.add(user);
    }
    public void removeTagUser(User user) {
        if (tagUser.contains(user)) tagUser.remove(user);
    }
    public void addLike(Like like) {
        if (!likes.contains(like)) likes.add(like);
    }
    public void removeLike(Like like) {
        if (likes.contains(like)) likes.remove(like);
    }
}
