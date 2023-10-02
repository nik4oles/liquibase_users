package com.example.liquibase_users1.models.entity;


import com.example.liquibase_users1.models.enums.Gender;
import com.example.liquibase_users1.models.enums.Popularity;
import com.example.liquibase_users1.models.enums.Status;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Setter
@Getter
@EqualsAndHashCode
@NamedEntityGraphs({
        @NamedEntityGraph(name = "userForSubscribeGraph",
                attributeNodes = {@NamedAttributeNode(value = "subscribers")})})

public class User {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String lastname;
    @Column//(nullable = false)
    // @UniqueElements
    private String nickname;
    private String email;
   // @Column(nullable = false)
    private String password;
    private String shortStoryAboutUser;
   // @Column(nullable = false)
    private short number;
    private LocalDate dateOfBirth;
    private LocalDate reg_date;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_subscribers",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "subscribers_id")
    )
    private List<User> subscribers;
    @OneToMany
    @JoinTable(name = "users_albums",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "albums_id")
    )
    private List<Album> albums;
    @ManyToMany
    @JoinTable(name = "users_groups",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "groups_id")
    )
    private List<Group> groups;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
    @OneToOne
    @JoinColumn(name = "photo_id")
    private Photo profilePicture;
    @OneToOne
    @JoinColumn(name = "albums_id")
    private Album mainAlbum;
    @Embedded
    private Location location;
    private boolean isPrivate;
    private boolean enabled;

    private Popularity popularity;

    public User() {

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
}
