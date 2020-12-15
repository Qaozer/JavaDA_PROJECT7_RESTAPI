package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.BidListService;
import com.nnk.springboot.domain.BidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@Controller
public class BidListController {
    @Autowired
    private BidListService bidListService;

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        // TODO: call service find all bids to show to the view
        List<BidList> bidList = bidListService.findAll();
        model.addAllAttributes(bidList);
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (result.hasErrors()){
            return "/bidList/add";
        }
        bidListService.saveBid(bid);
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        Optional<BidList> bid = bidListService.findById(id);
        if(bid.isPresent()){
            model.addAttribute("bid", bid.get());
        }
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if(!result.hasErrors()){
            Optional<BidList> bid = bidListService.findById(id);
            if(bid.isPresent()){
                bidListService.updateBid(bid.get(),bidList);
            }
        }
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list
        Optional<BidList> bidList = bidListService.findById(id);
        if(bidList.isPresent()){
            bidListService.deleteById(id);
        }
        return "redirect:/bidList/list";
    }
}
