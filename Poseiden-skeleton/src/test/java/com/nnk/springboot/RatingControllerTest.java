package com.nnk.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowRatingAdmin() throws Exception{
        this.mockMvc.perform(get("/rating/list")).andExpect(status().isOk());
    }

    @WithMockUser()
    @Test
    public void testShowRating() throws Exception{
        this.mockMvc.perform(get("/rating/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddRatingAdmin() throws Exception{
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddRating() throws Exception{
        this.mockMvc.perform(get("/rating/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateRatingAdmin() throws Exception{
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating","moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
        ).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testValidateRating() throws Exception{
        this.mockMvc.perform(post("/rating/validate")
                .param("moodysRating","moodysRating")
                .param("sandPRating", "sandPRating")
                .param("fitchRating", "fitchRating")
                .param("orderNumber", "1")
        ).andExpect(status().isForbidden());
    }
}
