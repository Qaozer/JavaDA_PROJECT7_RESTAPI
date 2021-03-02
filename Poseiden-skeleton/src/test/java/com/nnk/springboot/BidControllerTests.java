package com.nnk.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowBidListAdmin() throws Exception{
       this.mockMvc.perform(get("/bidList/list")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testShowBidList() throws Exception{
        this.mockMvc.perform(get("/bidList/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddBidListAdmin() throws Exception{
       this.mockMvc.perform(get("/bidList/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddBidList() throws Exception{
       this.mockMvc.perform(get("/bidList/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateBidListAdmin() throws Exception{
       this.mockMvc.perform(post("/bidList/validate")
               .param("account","account")
               .param("type", "type")
               .param("bidQuantity", "1")
       ).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testValidateBidList() throws Exception{
       this.mockMvc.perform(post("/bidList/validate")
               .param("account","account")
               .param("type", "type")
               .param("bidQuantity", "1")
       ).andExpect(status().isForbidden());
    }
}
