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
class GroupRestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    private void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createGroup() throws Exception {
        String request = "{ \"name\": \"name1\", \"description\": \"lname1\", \"ownerId\":  1,\"profilePicture\": null, \"shortStoryAboutGroup\": null, \"private\": false}";

        mockMvc.perform(post("/groups/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void getGroup() throws Exception {
        mockMvc.perform(get("/groups/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateGroup() throws Exception {
        String request = "{ \"name\": \"UPDATE\", \"description\": \"lname1\", \"ownerId\":  1,\"profilePicture\": null, \"shortStoryAboutGroup\": null, \"private\": false}";

        mockMvc.perform(put("/groups/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void deleteGroup() throws Exception {

        mockMvc.perform(delete("/groups/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void subscribe() throws Exception {

        mockMvc.perform(post("/groups/{id}/subscribe/{subscriberId}", 3L, 7L))
                .andExpect(status().isOk());
    }

    @Test
    void unsubscribe() throws Exception {
        mockMvc.perform(post("/groups/{id}/unsubscribe/{subscriberId}", 3L, 7L))
                .andExpect(status().isOk());
    }

    @Test
    void createAlbum() throws Exception {
        String request = "{\"name\": \"Create\", \"description\": \"hello\", \"date_creation\": null}";

        mockMvc.perform(post("/groups/{id}/albums", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }
}