package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.TradeService;
import com.nnk.springboot.domain.Trade;
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
public class TradeController {
    @Autowired
    private TradeService tradeService;

    private static Logger logger = LoggerFactory.getLogger(TradeController.class);

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /trade/list");
        model.addAttribute("trades",tradeService.findAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        logger.info("[GET] Accessing /trade/add");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("[POST] Accessing /trade/validate");
        if(!result.hasErrors()){
            tradeService.save(trade);
            model.addAttribute("trades", tradeService.findAll());
            logger.info("[POST] Trade saved");
            return "redirect:/trade/list";
        }
        logger.info("[POST] Trade not saved, form contains errors : " + result.getAllErrors().toString());
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /trade/update/"+id);
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                               BindingResult result, Model model) {
        logger.info("[POST] Accessing /trade/update/"+id);
        if(result.hasErrors()){
            logger.info("[POST] Trade not updated, form contains errors : " + result.getAllErrors().toString());
            return "trade/update";
        }

        tradeService.update(trade, id);
        model.addAttribute("trades", tradeService.findAll());
        logger.info("[POST] Trade updated");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /trade/delete/"+id);
        tradeService.delete(id);
        model.addAttribute("trades", tradeService.findAll());
        logger.info("[DEL] Trade deleted");
        return "redirect:/trade/list";
    }
}
