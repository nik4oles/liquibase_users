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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LiquibaseUsers1Application.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class SearchGroupRestControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    private void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void getAllGroupsByParams() throws Exception {
        mockMvc.perform(get("/search/groups/page/{pageNumber}", 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "name6")
                .param("description", "lname6"))
                .andExpect(status().isOk());


    }

    @Test
    void getGroupsByUserIdAndParams() throws Exception {
        mockMvc.perform(get("/search/groups/users/{id}/page/{pageNumber}", 1L, 0L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("name", "name2")
                        .param("description", "lname2"))
                .andExpect(status().isOk());
    }
}