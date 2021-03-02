package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.RuleNameService;
import com.nnk.springboot.domain.RuleName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RuleNameController {
    @Autowired
    private RuleNameService ruleNameService;

    private static Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /ruleName/list");
        model.addAttribute("ruleNames",ruleNameService.findAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleNameForm(RuleName ruleName) {
        logger.info("[GET] Accessing /ruleName/add");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        logger.info("[POST] Accessing /ruleName/validate");
        if(!result.hasErrors()){
            ruleNameService.save(ruleName);
            model.addAttribute("ruleNames", ruleNameService.findAll());
            logger.info("[POST] Rulename saved");
            return "redirect:/ruleName/list";
        }
        logger.info("[POST] Rulename not saved, form contains errors : " + result.getAllErrors().toString());
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /ruleName/update/"+id);
        RuleName ruleName = ruleNameService.findById(id);
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                               BindingResult result, Model model) {
        logger.info("[POST] Accessing /ruleName/update/"+id);
        if(result.hasErrors()){
            logger.info("[POST] Rulename not updated, form contains errors : " + result.getAllErrors().toString());
            return "ruleName/update";
        }

        ruleNameService.update(ruleName, id);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        logger.info("[POST] Rulename updated");
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /ruleName/delete/"+id);
        ruleNameService.delete(id);
        model.addAttribute("ruleNames", ruleNameService.findAll());
        logger.info("[DEL] Rulename deleted");
        return "redirect:/ruleName/list";
    }
}
