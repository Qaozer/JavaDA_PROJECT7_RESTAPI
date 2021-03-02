package com.nnk.springboot;

import com.nnk.springboot.Services.CurveService;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CurvePointControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private CurveService curveService;

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

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testShowUpdateBidListAdmin() throws Exception{
        CurvePoint curvePoint = curveService.save(new CurvePoint(42, 10.0d,10.0d));

        this.mockMvc.perform(get("/curvePoint/update/"+curvePoint.getId()))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("curveId",Matchers.equalTo(42))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("term",Matchers.equalTo(10.0d))))
                .andExpect(model().attribute("curvePoint", Matchers.hasProperty("value",Matchers.equalTo(10.0d))));
    }

    @WithMockUser
    @Transactional
    @Test
    public void testShowUpdateBidList() throws Exception{
        CurvePoint curvePoint = curveService.save(new CurvePoint(42, 10.0d,10.0d));

        this.mockMvc.perform(get("/curvePoint/update/"+curvePoint.getId())).andExpect(status().isForbidden());
    }

    @WithMockUser(authorities = "ADMIN")
    @Transactional
    @Test
    public void testDeleteBidListAdmin() throws Exception{
        CurvePoint curvePoint = curveService.save(new CurvePoint(42, 10.0d,10.0d));

        this.mockMvc.perform(get("/curvePoint/delete/"+curvePoint.getId())).andExpect(status().isFound()).andReturn();
    }
}
