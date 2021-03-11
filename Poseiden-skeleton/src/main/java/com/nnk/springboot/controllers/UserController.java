package com.nnk.springboot.controllers;

import com.nnk.springboot.Services.UserService;
import com.nnk.springboot.UserDto;
import com.nnk.springboot.domain.User;
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
public class UserController {
    @Autowired
    private UserService userService;

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Displays a list of users
     * @param model
     * @return
     */
    @RequestMapping("/user/list")
    public String home(Model model)
    {
        logger.info("[GET] Accessing /user/list");
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Displays a page to create a new user
     * @param user
     * @return
     */
    @GetMapping("/user/add")
    public String addUser(User user) {
        logger.info("[GET] Accessing /user/add");
        return "user/add";
    }

    /**
     * Validates a user before saving it in database
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result, Model model) {
        logger.info("[POST] Accessing /user/validate");
        if (!result.hasErrors()) {
            userService.add(user);
            model.addAttribute("users", userService.findAll());
            logger.info("[POST] User saved");
            return "redirect:/user/list";
        }
        logger.info("[POST] User not saved, form contains errors : " + result.getAllErrors().toString());
        return "user/add";
    }

    /**
     * Show the page to update a user
     * @param id the user id
     * @param model
     * @return
     */
    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /user/update/"+id);
        User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid User Id:" + id));
        UserDto userDto = new UserDto(user.getId(), "",user.getFullname(), user.getRole());
        model.addAttribute("userDto", userDto);
        return "user/update";
    }

    /**
     * Updates the user if no error was found
     * @param id the user id
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserDto user,
                             BindingResult result, Model model) {
        logger.info("[POST] Accessing /user/update/"+id);
        if (result.hasErrors()) {
            logger.info("[POST] User not updated, form contains errors : " + result.getAllErrors().toString());
            return "user/update";
        }

        userService.update(user, id);
        model.addAttribute("users", userService.findAll());
        logger.info("[POST] User updated");
        return "redirect:/user/list";
    }

    /**
     * Deletes a user
     * @param id the user id
     * @param model
     * @return
     */
    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        logger.info("[GET] Accessing /user/delete/"+id);
        userService.delete(id);
        model.addAttribute("users", userService.findAll());
        logger.info("[DEL] User deleted");
        return "redirect:/user/list";
    }
}
