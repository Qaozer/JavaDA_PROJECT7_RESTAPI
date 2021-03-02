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
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testShowCurvePointAdmin() throws Exception{
        this.mockMvc.perform(get("/curvePoint/list")).andExpect(status().isOk());
    }

    @WithMockUser()
    @Test
    public void testShowCurvePoint() throws Exception{
        this.mockMvc.perform(get("/curvePoint/list")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testAddCurvePointAdmin() throws Exception{
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testAddCurvePoint() throws Exception{
        this.mockMvc.perform(get("/curvePoint/add")).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Test
    public void testValidateCurvePointAdmin() throws Exception{
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId","curveId")
                .param("term", "10.0")
                .param("value", "10.0")
        ).andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void testValidateCurvePoint() throws Exception{
        this.mockMvc.perform(post("/curvePoint/validate")
                .param("curveId","curveId")
                .param("term", "10.0")
                .param("value", "10.0")
        ).andExpect(status().isForbidden());
    }
}
