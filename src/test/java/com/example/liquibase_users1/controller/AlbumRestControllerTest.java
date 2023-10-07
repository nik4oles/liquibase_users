package com.example.liquibase_users1.controller;

import com.example.liquibase_users1.LiquibaseUsers1Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes =  LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class AlbumRestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    private void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void getAlbum() throws Exception {

        mockMvc.perform(get("/albums/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateAlbum() throws Exception {
        String request = "{\"name\": \"Update2\", \"description\": \"hello\", \"date_creation\": null}";

        mockMvc.perform(put("/albums/{id}", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/albums/{id}", 5L))
                .andExpect(status().isOk());
    }

    @Test
    void addAlbumPhoto() throws Exception {
        String request = "{\"uri\": \"file:///Users/Username/PicturesNew/photo10.jpg\", " +
                "\"title\": \"addAlbumPhoto\"}";

        mockMvc.perform(post("/albums/{id}/photos", 4L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }
}