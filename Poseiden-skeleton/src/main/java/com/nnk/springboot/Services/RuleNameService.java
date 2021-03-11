package com.nnk.springboot.Services;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RuleNameService {
    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Retrieves a list of RuleNames from the database
     * @return a list of RuleNames
     */
    @Transactional(readOnly=true)
    public List<RuleName> findAll(){ return ruleNameRepository.findAll();}

    /**
     * Saves a RuleName in database
     * @param ruleName the ruleName
     * @return the ruleName in database
     */
    public RuleName save(RuleName ruleName){
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Retrieves a ruleName from the database
     * @param id the ruleName id
     * @return the ruleName or an Exception if not found
     */
    @Transactional(readOnly=true)
    public RuleName findById(int id){
        return ruleNameRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid ruleName Id:" + id));
    }

    /**
     * Updates a ruleName in database
     * @param ruleName the updated ruleName
     * @param id the ruleName id in database
     * @return the ruleName in database
     */
    public RuleName update(RuleName ruleName, int id){
        ruleName.setId(id);
        return ruleNameRepository.save(ruleName);
    }

    /**
     * Deletes a ruleName from the database
     * @param id the ruleName id
     */
    public void delete(int id){
        ruleNameRepository.deleteById(id);
    }
}
