package com.example.liquibase_users1.service.impl;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import com.example.liquibase_users1.converter.photo.PhotoMapper;
import com.example.liquibase_users1.models.dto.response.PhotoResponseDTO;
import com.example.liquibase_users1.models.entity.Like;
import com.example.liquibase_users1.models.entity.Photo;
import com.example.liquibase_users1.repository.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class LikeServiceImplTest {
    @Mock
    private LikeRepository likeRepository;
    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private PhotoMapper photoMapper;
    @InjectMocks
    private LikeServiceImpl likeService;

    @Test
    void removeLikePhoto() {
        // Arrange
        long likeId = 1L;
        long photoId = 2L;
        Like like = new Like();
        Photo photo = new Photo();
        PhotoResponseDTO photoResponseDTO = new PhotoResponseDTO();
        photoResponseDTO.setLike(1);
        photo.getLikes().add(like);
        when(photoRepository.getPhotoById(photoId)).thenReturn(photo);
        when(likeRepository.getLikeById(likeId)).thenReturn(like);
        when(photoRepository.save(photo)).thenReturn(photo);
        when(photoMapper.toDto(photo)).thenReturn(photoResponseDTO);


        // Act
        PhotoResponseDTO result = likeService.removeLikePhoto(likeId, photoId);

        // Assert
        assertNotNull(result);
        assertEquals(photoResponseDTO, result);
        assertFalse(photo.getLikes().contains(like));
        verify(likeRepository, times(1)).deleteById(likeId);
        verify(photoRepository, times(1)).save(photo);
    }

    @Test
    void getLikesByPhoto() {
        // Arrange
        long photoId = 1L;
        List<Like> likes = new ArrayList<>();
        likes.add(new Like());
        likes.add(new Like());
        when(photoRepository.getLikesPhotoById(photoId)).thenReturn(likes);

        // Act
        List<Like> result = likeService.getLikesByPhoto(photoId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}