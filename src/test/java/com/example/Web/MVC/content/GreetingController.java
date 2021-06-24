package com.example.Web.MVC.content;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

	/*
	 * This will serve a user a webpage when they go to <website>/greeting
	 * You can reach this website at: localhost:8080/greeting
	 * Note: this method also takes an optional argument (name) in the URL with a defualt value or World
	 * 		This means that if the webpage was reached with a name variable, it will serve something different
	 * 		example: <webstie>/greeting?name=Ben will say Hello, Ben! instead of Hello, World!
	 */
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name="name", required=false, defaultValue="Earth") String name, Model model) {
		model.addAttribute("name", name);
		return "greeting";
	}

}