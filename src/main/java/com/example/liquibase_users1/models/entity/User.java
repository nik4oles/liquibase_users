package com.example.liquibase_users1.models.entity;


import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.models.enums.Popularity;
import com.example.liquibase_users1.models.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@NamedEntityGraphs({
        @NamedEntityGraph(name = "userForSubscribeGraph",
                attributeNodes = {@NamedAttributeNode(value = "subscribers")})})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String lastname;
    private String nickname;
    private String email;
    private String password;
    private String shortStoryAboutUser;
    private String number;
    private LocalDate dateOfBirth;
    private LocalDate reg_date;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_subscribers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscriber_id")
    )
    private List<User> subscribers = new ArrayList<>();
    @OneToMany
    @JoinTable(name = "users_albums",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id")
    )
    private List<Album> albums = new ArrayList<>();
    @ManyToMany
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id")
    )
    private List<Group> groups;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToOne
    @JoinColumn(name = "profile_photo_id")
    private Photo profilePicture;
    @OneToOne
    @JoinColumn(name = "main_album_id")
    private Album mainAlbum;
    @Embedded
    private Location location;
    private boolean isPrivate;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    private Popularity popularity;

    public User() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email);
    }

    public void createNewUser() {
        this.mainAlbum = new Album();
        mainAlbum.setName("main");
        albums = new ArrayList<>();
        albums.add(mainAlbum);
        reg_date = LocalDate.now();
        enabled = true;
        isPrivate = false;
    }

    public void addSubscriber(User user) {
        if (!subscribers.contains(user)) subscribers.add(user);
        if (subscribers.size() == 5 || subscribers.size() == 8 || subscribers.size() == 10) calculatePopularity();
    }

    public void deleteSubscriber(User user) {
        if (subscribers.contains(user)) subscribers.remove(user);
        if (subscribers.size() == 5 || subscribers.size() == 8 || subscribers.size() == 10) calculatePopularity();
    }

    public void addNewAlbum(Album album) {
        albums.add(album);

    }

    public void deleteAlbum(Album album) {
        if (albums.contains(album)) {
            albums.remove(album);
        }
    }

    public void addGroup(Group group) {
        if (!groups.contains(group)) groups.add(group);
    }

    public void deleteGroup(Group group) {
        if (groups.contains(group)) groups.remove(group);
    }

    public void calculatePopularity() {
        if (subscribers.size() == 5) {
            popularity = Popularity.SILVER;
            return;
        }
        popularity = popularity == Popularity.GOLD && subscribers.size() == 10 && likesOnPhoto() ? Popularity.DIAMOND : Popularity.GOLD;
    }

    private boolean likesOnPhoto() {
        long like = albums.stream()
                .flatMap(album -> album.getPhotos().stream()
                        .map(photo -> photo.getLikes().size()))
                .count();
//        long like = albums.stream()
//                .flatMap(album -> album.getPhotos().values().stream()
//                        .map(photo -> photo.getLikes().size()))
//                .count();
        long photo = albums.stream().map(album -> album.getPhotos().size()).count();
        return like / photo >= photo / 2;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", number='" + number + '\'' +
                ", reg_date=" + reg_date +
                ", profilePicture=" + profilePicture +
                ", isPrivate=" + isPrivate +
                ", popularity=" + popularity +
                '}';
    }
}
