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

@SpringBootTest(classes = LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class UserRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    private void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void createUser() throws Exception {
        String request = "{\"name\": \"createUser\", \"lastname\": \"createUser\", \"nickname\": \"@Nick4\", \"email\": \"user4@skillbox.ru\", \"password\": \"4444\", \"number\": 0, \"dateOfBirth\": null,\"gender\": null}";

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void getUserResponseDTO() throws Exception {

        mockMvc.perform(get("/users/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getUserResponseDTOnByNickname() throws Exception {

        mockMvc.perform(get("/users/nicknames/{nickname}", "@Nick1"))
                .andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {

        String request = "{\"name\": \"name1\", \"lastname\": \"lname1\", \"nickname\": \"@Nick1\", " +
                "\"email\": \"user1@skillbox.ru\", \"password\": \"1111\", \"shortStoryAboutUser\": null, " +
                "\"number\": 0, \"dateOfBirth\": null, \"gender\": \"NOT_SPECIFIED\", \"status\": null, " +
                "\"location\": {\"country\" : \"country1\", \"city\": \"city1\"}, \"private\": false}";

        mockMvc.perform(put("/users/{id}", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        mockMvc.perform(delete("/users/{id}", 11L))
                .andExpect(status().isOk());

    }

    @Test
    void deleteUserByNickname() throws Exception {

        mockMvc.perform(delete("/users/nicknames/{nickname}", "@Nick6"))
                .andExpect(status().isOk());
    }

    @Test
    void subscribe() throws Exception {

        mockMvc.perform(post("/users/{id}/subscribe/{subscriberId}", 12L, 4L))
                .andExpect(status().isOk());
    }

    @Test
    void unsubscribe() throws Exception {

        mockMvc.perform(post("/users/{id}/unsubscribe/{subscriberId}", 12L, 4L))
                .andExpect(status().isOk());
    }

    @Test
    void createAlbum() throws Exception {

        String request = "{\"name\": \"createAlbumUser\", \"description\": \"hello\", \"date_creation\": null}";

        mockMvc.perform(post("/users/{id}/albums", 3L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }
}