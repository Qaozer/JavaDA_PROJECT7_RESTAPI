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
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowUserAdmin() throws Exception{
        this.mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testShowUser() throws Exception{
        this.mockMvc.perform(get("/user/list")).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddUserAdmin() throws Exception{
        this.mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddUser() throws Exception{
        this.mockMvc.perform(get("/user/add")).andExpect(status().isOk());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateUserAdmin() throws Exception{
        this.mockMvc.perform(post("/user/validate")
                .param("fullname","fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "USER")
        ).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testValidateUser() throws Exception{
        this.mockMvc.perform(post("/user/validate")
                .param("fullname","fullname")
                .param("username", "username")
                .param("password", "password")
                .param("role", "USER")
        ).andExpect(status().isForbidden());
    }
}
