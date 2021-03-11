package com.nnk.springboot.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController
{
	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Home page
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String home(Model model)
	{
		logger.info("[GET] Accessing /");
		return "home";
	}

	/**
	 * Admin home page
	 * @param model
	 * @return
	 */
	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		logger.info("[GET] Accessing /admin/home");
		return "redirect:/bidList/list";
	}

	/**
	 * Forbidden access page
	 * @param model
	 * @return
	 */
	@RequestMapping("/403")
	public String accessDenied(Model model){
		logger.info("[GET] Access Forbidden");
		model.addAttribute("errorMsg","Only ADMINS can access this page");
		return "403";
	}

}
