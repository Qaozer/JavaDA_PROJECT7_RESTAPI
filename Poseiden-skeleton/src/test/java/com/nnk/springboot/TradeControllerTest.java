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
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowTradeAdmin() throws Exception{
        this.mockMvc.perform(get("/trade/list")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testShowTrade() throws Exception{
        this.mockMvc.perform(get("/trade/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddTradeAdmin() throws Exception{
        this.mockMvc.perform(get("/trade/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddTrade() throws Exception{
        this.mockMvc.perform(get("/trade/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateTradeAdmin() throws Exception{
        this.mockMvc.perform(post("/trade/validate")
                .param("account","account")
                .param("type", "type")
                .param("buyQuantity", "10.0")
        ).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testValidateTrade() throws Exception{
        this.mockMvc.perform(post("/trade/validate")
                .param("account","account")
                .param("type", "type")
                .param("buyQuantity", "10.0")
        ).andExpect(status().isForbidden());
    }
}
