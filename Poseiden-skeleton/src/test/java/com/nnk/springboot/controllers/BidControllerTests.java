package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.BidListService;
import com.nnk.springboot.domain.BidList;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BidControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BidListService bidListService;

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
    @Transactional
    public void testValidateBidListAdmin() throws Exception{
       this.mockMvc.perform(post("/bidList/validate")
               .param("account","account")
               .param("type", "type")
               .param("bidQuantity", "1")
               .with(csrf())
       ).andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    @Transactional
    public void testValidateBidListAdminHasErrors() throws Exception{
        this.mockMvc.perform(post("/bidList/validate")
                .param("account","account")
                .param("type", "type")
                .param("bidQuantity", "B")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser
    @Test
    public void testValidateBidList() throws Exception{
       this.mockMvc.perform(post("/bidList/validate")
               .param("account","account")
               .param("type", "type")
               .param("bidQuantity", "1")
               .with(csrf())
       ).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testShowUpdateBidListAdmin() throws Exception{
        BidList bid = bidListService.save(new BidList("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/bidList/update/"+bid.getBidListId()))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("account",Matchers.equalTo("Account"))))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("type",Matchers.equalTo("Type"))))
                .andExpect(model().attribute("bidList", Matchers.hasProperty("bidQuantity",Matchers.equalTo(10.0d))));
    }

    @WithMockUser
    @Transactional
    @Test
    public void testShowUpdateBidList() throws Exception{
        BidList bid = bidListService.save(new BidList("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/bidList/update/"+bid.getBidListId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testUpdateBidListAdmin() throws Exception{
        BidList bid = bidListService.save(new BidList("Account", "Type", 10.0d));
        this.mockMvc.perform(post("/bidList/update/"+bid.getBidListId())
                .param("account","nuAccount")
                .param("type","nuType")
                .param("bidQuantity","12.0")
                .with(csrf())
        ).andExpect(redirectedUrl("/bidList/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testUpdateBidListAdminHasError() throws Exception{
        BidList bid = bidListService.save(new BidList("Account", "Type", 10.0d));
        this.mockMvc.perform(post("/bidList/update/"+bid.getBidListId())
                .param("account","nuAccount")
                .param("type","nuType")
                .param("bidQuantity","A.0")
                .with(csrf())
        ).andExpect(model().hasErrors());
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testDeleteBidListAdmin() throws Exception{
        BidList bid = bidListService.save(new BidList("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/bidList/delete/"+bid.getBidListId())).andExpect(status().isFound()).andReturn();
    }
}
