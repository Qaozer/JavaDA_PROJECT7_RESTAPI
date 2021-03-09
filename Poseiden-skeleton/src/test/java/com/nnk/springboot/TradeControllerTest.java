package com.nnk.springboot;

import com.nnk.springboot.Services.TradeService;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.Trade;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private TradeService tradeService;

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
        ).andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateTradeAdminHasError() throws Exception{
        this.mockMvc.perform(post("/trade/validate")
                .param("type", "type")
                .param("buyQuantity", "10.0")
        ).andExpect(model().hasErrors());
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

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testShowUpdateTradeAdmin() throws Exception{
        Trade trade = tradeService.save(new Trade("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/trade/update/"+trade.getTradeId()))
                .andExpect(model().attribute("trade", Matchers.hasProperty("account",Matchers.equalTo("Account"))))
                .andExpect(model().attribute("trade", Matchers.hasProperty("type",Matchers.equalTo("Type"))))
                .andExpect(model().attribute("trade", Matchers.hasProperty("buyQuantity",Matchers.equalTo(10.0d))));
    }

    @WithMockUser
    @Transactional
    @Test
    public void testShowUpdateTrade() throws Exception{
        Trade trade = tradeService.save(new Trade("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/trade/update/"+trade.getTradeId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testUpdateTradeAdmin() throws Exception{
        Trade trade = tradeService.save(new Trade("Account", "Type", 10.0d));
        this.mockMvc.perform(post("/trade/update/"+trade.getTradeId())
                .param("account","account")
                .param("type", "type")
                .param("buyQuantity", "10.0")).andExpect(redirectedUrl("/trade/list"));
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testUpdateTradeAdminHasError() throws Exception{
        Trade trade = tradeService.save(new Trade("Account", "Type", 10.0d));
        this.mockMvc.perform(post("/trade/update/"+trade.getTradeId())
                .param("account","account")
                .param("type", "type")
                .param("buyQuantity", "A.0")).andExpect(model().hasErrors());
    }
    

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testDeleteTradeAdmin() throws Exception{
        Trade trade = tradeService.save(new Trade("Account", "Type", 10.0d));

        this.mockMvc.perform(get("/trade/delete/"+trade.getTradeId())).andExpect(status().isFound()).andReturn();
    }
}
