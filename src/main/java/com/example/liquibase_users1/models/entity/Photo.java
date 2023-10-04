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
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
    private URI uri;
    private String title;
    @OneToMany
    @JoinTable(name = "photos_likes",
            joinColumns = @JoinColumn(name = "photo_id"),
            inverseJoinColumns = @JoinColumn(name = "like_id")
    )
    private List<Like> likes;
    @ManyToMany
    @JoinTable(name = "photos_tag_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "photo_id")
    )
    private List<User> tagUser;
    private LocalDate publicationDate;

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
