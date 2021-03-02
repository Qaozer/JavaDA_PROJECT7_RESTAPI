package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.CurveService;
import com.nnk.springboot.domain.CurvePoint;
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
public class CurveController {
    @Autowired
    private CurveService curveService;

    private static Logger logger = LoggerFactory.getLogger(CurveController.class);

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /curvepoint/list");
        model.addAttribute("curvePoints", curveService.findAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint curvePoint) {
        logger.info("[GET] Accessing /curvepoint/add");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        logger.info("[POST] Accessing /curvepoint/validate");
        if (!result.hasErrors()) {
            curveService.save(curvePoint);
            model.addAttribute("curvePoints", curveService.findAll());
            logger.info("[POST] Curvepoint saved");
            return "redirect:/curvePoint/list";
        }
        logger.info("[POST] Curvepoint not saved, form contains errors : " + result.getAllErrors().toString());
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /curvepoint/update/"+id);
        CurvePoint curvePoint = curveService.findById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        logger.info("[POST] Accessing /curvePoint/update/"+id);
        if(!result.hasErrors()){
            CurvePoint inDb = curveService.findById(id);
            curveService.update(inDb, curvePoint);
            logger.info("[POST] Curvepoint updated");
            return "redirect:/curvePoint/list";
        }
        logger.info("[POST] Curvepoint not updated, form contains errors : " + result.getAllErrors().toString());
        return "curvePoint/update";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /curvepoint/delete/"+id);
        curveService.delete(curveService.findById(id));
        logger.info("[DEL] Curvepoint deleted");
        return "redirect:/curvePoint/list";
    }
}
