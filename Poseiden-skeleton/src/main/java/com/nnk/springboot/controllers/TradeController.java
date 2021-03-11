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

    /**
     * Displays a list of trades
     * @param model
     * @return
     */
    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /trade/list");
        model.addAttribute("trades",tradeService.findAll());
        return "trade/list";
    }

    /**
     * Display the page to add a trade
     * @param trade
     * @return
     */
    @GetMapping("/trade/add")
    public String addTradeForm(Trade trade) {
        logger.info("[GET] Accessing /trade/add");
        return "trade/add";
    }

    /**
     * Validates a trade before saving it in database
     * @param trade
     * @param result
     * @param model
     * @return
     */
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

    /**
     * Displays the page to update a trade
     * @param id the trade id
     * @param model
     * @return
     */
    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /trade/update/"+id);
        Trade trade = tradeService.findById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    /**
     * Updates the trade if no error was found
     * @param id the trade id
     * @param trade
     * @param result
     * @param model
     * @return
     */
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

    /**
     * Deletes a trade
     * @param id the trade id
     * @param model
     * @return
     */
    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /trade/delete/"+id);
        try{
            tradeService.findById(id);
            tradeService.delete(id);
            logger.info("[DEL] Trade deleted");
        } catch (Exception e) {
            logger.info("[DEL] Invalid Trade id");
        }
        model.addAttribute("trades", tradeService.findAll());
        return "redirect:/trade/list";
    }
}
