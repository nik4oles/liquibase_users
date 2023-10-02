package com.example.liquibase_users1.models.entity;

import lombok.Data;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;

@Entity
@Data
@Table(name = "albums")
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate date_creation;
    private boolean isPrivate;
    @OneToMany(mappedBy = "album")
    private List<Photo> photos;


    public Album() {
    }
    public void createNewAlbum() {
        date_creation = LocalDate.now();
        photos = new ArrayList<>();
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, description, photos);
    }

    public void addAlbum(Photo photo) {
        if (!photos.contains(photo)) {
            photos.add(photo);
        }
    }

    public void deleteAlbum(Photo photo) {
        if (photos.contains(photo)) {
            photos.remove(photo);
        }
    }
//    public void addAlbum(Photo photo) {
//        if (!photos.containsValue(photo)) {photos.put(photo.getId(), photo);}
//    }
//
//    public void deleteAlbum(Photo photo) {
//        if (photos.containsKey(photo)) {photos.remove(photo);}
//    }

}
