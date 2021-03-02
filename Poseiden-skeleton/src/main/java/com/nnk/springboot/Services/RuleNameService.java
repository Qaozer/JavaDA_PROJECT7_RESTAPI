package com.nnk.springboot.Services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    public List<RuleName> findAll(){ return ruleNameRepository.findAll();}

    public RuleName save(RuleName ruleName){
        return ruleNameRepository.save(ruleName);
    }

    public RuleName findById(int id){
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    public RuleName update(RuleName ruleName, int id){
        ruleName.setId(id);
        return ruleNameRepository.save(ruleName);
    }

    public void delete(int id){
        RuleName ruleName = this.findById(id);
        ruleNameRepository.delete(ruleName);
    }
}
