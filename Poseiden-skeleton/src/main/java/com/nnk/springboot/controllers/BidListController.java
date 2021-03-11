package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.BidListService;
import com.nnk.springboot.domain.BidList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Controller for bidlist
 */
@Controller
public class BidListController {
    @Autowired
    private BidListService bidListService;

    private static Logger logger = LoggerFactory.getLogger(BidListController.class);

    /**
     * Displays a list of bidlists
     * @param model
     * @return
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /Bidlist/list");
        model.addAttribute("bids", bidListService.findAll());
        return "bidList/list";
    }

    /**
     * Displays the page to add a bidlist
     * @param bid
     * @return
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        logger.info("[GET] Accessing /Bidlist/add");
        return "bidList/add";
    }

    /**
     * Validation of a newly created bidlist
     * @param bid
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        logger.info("[POST] Accessing /Bidlist/validate");
        if (result.hasErrors()){
            logger.info("[POST] Bidlist not saved, form contains errors : " + result.getAllErrors().toString());
            return "/bidList/add";
        }
        bidListService.save(bid);
        model.addAttribute("bids", bidListService.findAll());
        logger.info("[POST] Bidlist saved");
        return "redirect:/bidList/list";
    }

    /**
     * Displays a page to update a bidlist
     * @param id the bidListId in database
     * @param model
     * @return
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /Bidlist/update/"+id);
        Optional<BidList> bid = bidListService.findById(id);
        if(bid.isPresent()){
            model.addAttribute("bidList", bid.get());
            return "bidList/update";
        }
        return "redirect:/bidList/list";
    }

    /**
     * Updates the bidlist in database if no error was found
     * @param id the bidListId in database
     * @param bidList
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        logger.info("[POST] Accessing /Bidlist/update/"+id);
        if(!result.hasErrors()){
            Optional<BidList> bid = bidListService.findById(id);
            if(bid.isPresent()){
                bidListService.update(bidList, id);
                logger.info("[POST] Bidlist updated");
            }
            return "redirect:/bidList/list";
        } else {
            logger.info("[POST] Bidlist not updated, form contains errors : " + result.getAllErrors().toString());
            return "/bidList/update";
        }
    }

    /**
     * Delete a bidlist
     * @param id the bidListId in database
     * @param model
     * @return
     */
    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /Bidlist/delete/"+id);
        Optional<BidList> bidList = bidListService.findById(id);
        if(bidList.isPresent()){
            bidListService.deleteById(id);
            logger.info("[DEL] Bidlist deleted");
        } else {
            logger.info("[GET] Invalid bidlist id");
        }
        return "redirect:/bidList/list";
    }
}
