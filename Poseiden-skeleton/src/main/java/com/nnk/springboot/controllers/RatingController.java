package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.RatingService;
import com.nnk.springboot.domain.Rating;
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
public class RatingController {
    @Autowired
    private RatingService ratingService;

    private static Logger logger = LoggerFactory.getLogger(RatingController.class);

    /**
     * Displays a list of ratings
     * @param model
     * @return
     */
    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /rating/list");
        model.addAttribute("ratings",ratingService.findAll());
        return "rating/list";
    }

    /**
     * Displays the page to add a rating
     * @param rating
     * @return
     */
    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        logger.info("[GET] Accessing /rating/add");
        return "rating/add";
    }

    /**
     * Validation of a newly created rating
     * @param rating
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        logger.info("[POST] Accessing /rating/validate");
        if(!result.hasErrors()){
            ratingService.save(rating);
            model.addAttribute("ratings", ratingService.findAll());
            logger.info("[POST] Rating saved");
            return "redirect:/rating/list";
        }
        logger.info("[POST] Rating not saved, form contains errors : " + result.getAllErrors().toString());
        return "rating/add";
    }

    /**
     * Displays the page to update a rating
     * @param id the rating id in database
     * @param model
     * @return
     */
    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /rating/update/"+id);
        Rating rating = ratingService.findById(id);
       model.addAttribute("rating", rating);
        return "rating/update";
    }

    /**
     * Updates the rating in database if no error was found
     * @param id the rating id in database
     * @param rating
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        logger.info("[POST] Accessing /rating/update/"+id);
        if(result.hasErrors()){
            logger.info("[POST] Rating not updated, form contains errors : " + result.getAllErrors().toString());
            return "rating/update";
        }

        ratingService.update(rating, id);
        model.addAttribute("ratings", ratingService.findAll());
        logger.info("[POST] rating updated");
        return "redirect:/rating/list";
    }

    /**
     * Deletes a rating
     * @param id the rating id in database
     * @param model
     * @return
     */
    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /rating/delete/"+id);
        try{
            ratingService.findById(id);
            ratingService.delete(id);
            logger.info("[DEL] Rating deleted");
        } catch (Exception e) {
            logger.info("[DEL] Invalid Rating id");
        }
        model.addAttribute("ratings", ratingService.findAll());
        return "redirect:/rating/list";
    }
}
