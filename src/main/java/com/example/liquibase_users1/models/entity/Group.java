package com.example.liquibase_users1.models.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "groups")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "groupGraph",
                attributeNodes = {
                        @NamedAttributeNode(value = "subscribers")
                }
        )
})
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private LocalDate reg_date;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;
    @Column
    @OneToMany
    @JoinTable(name = "groups_subscribers",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private List<User> subscribers;
    @Column
    @OneToMany
    @JoinTable(name = "groups_albums",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private List<Album> albums = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo profilePicture;
    private String shortStoryAboutGroup;
    @Column
    private boolean isPrivate;
    private boolean enabled;
    private boolean isOfficialGroup;

    public Group() {
        reg_date = LocalDate.now();
        enabled = true;
        isPrivate = false;
        isOfficialGroup = false;
    }

    public void addSubscriber(User user) {
        if (!subscribers.contains(user)) subscribers.add(user);
    }

    public void deleteSubscriber(User user) {
        if (subscribers.contains(user)) subscribers.remove(user);
    }

    public void addAlbum(Album album) {
        if (!albums.contains(album)) {
            albums.add(album);
        }
    }

    public void deleteAlbum(Album album) {
        if (albums.contains(album)) {
            albums.remove(album);
        }
    }

}
