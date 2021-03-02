package com.nnk.springboot;

import com.nnk.springboot.Services.RuleNameService;
import com.nnk.springboot.domain.RuleName;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class RuleServiceTest {
    @Autowired
    private RuleNameService ruleService;

    @Test
    public void saveTest(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule = ruleService.save(rule);
        int id = rule.getId();
        assertNotNull(rule.getId());
        RuleName inDb = ruleService.findById(id);
        Assert.assertEquals(rule.getName(), inDb.getName());
        Assert.assertEquals(rule.getDescription(), inDb.getDescription());
        Assert.assertEquals(rule.getJson(), inDb.getJson());
        Assert.assertEquals(rule.getTemplate(), inDb.getTemplate());
        Assert.assertEquals(rule.getSqlPart(), inDb.getSqlPart());
        Assert.assertEquals(rule.getSqlStr(), inDb.getSqlStr());
    }

    @Test
    public void findByIdTest(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule = ruleService.save(rule);
        assertNotNull(ruleService.findById(rule.getId()));
    }

    @Test
    public void findAllTest(){
        int count = ruleService.findAll().size();

        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule = ruleService.save(rule);
        assertEquals(count + 1, ruleService.findAll().size());
    }

    @Test
    public void updateRatingTest(){
        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");
        rule = ruleService.save(rule);

        int id = rule.getId();

        RuleName nuRule = new RuleName("uRule Name", "uDescription", "uJson", "uTemplate", "uSQL", "uSQL Part");
        rule = ruleService.update(nuRule, id);
        assertEquals(id, rule.getId());
        assertEquals(nuRule.getName(), rule.getName());
        assertEquals(nuRule.getDescription(), rule.getDescription());
        assertEquals(nuRule.getJson(), rule.getJson());
        assertEquals(nuRule.getTemplate(), rule.getTemplate());
        assertEquals(nuRule.getSqlStr(), rule.getSqlStr());
        assertEquals(nuRule.getSqlPart(), rule.getSqlPart());
    }

    @Test
    public void deleteRuleTest(){
        int count = ruleService.findAll().size();

        RuleName rule = new RuleName("Rule Name", "Description", "Json", "Template", "SQL", "SQL Part");        rule = ruleService.save(rule);

        assertEquals(count + 1, ruleService.findAll().size());

        ruleService.delete(rule.getId());

        assertEquals(count, ruleService.findAll().size());
    }
}
