package com.example.Web.MVC.content;

import java.util.LinkedList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import Database.Records.PartRecord;

@Controller
public class GreetingController {

	/*
	 * This will serve a user a webpage when they go to <website>/greeting
	 * You can reach this website at: localhost:8080/greeting
	 * Note: this method also takes an optional argument (name) in the URL with a defualt value or World
	 * 		This means that if the webpage was reached with a name variable, it will serve something different
	 * 		example: <webstie>/greeting?name=Ben will say Hello, Ben! instead of Hello, World!
	 */
	@GetMapping("/a_home")
	public String greeting_a(Model model) {
		return "a_home";
	}
	
	@GetMapping("/c_orders")
	public String greeting(Model model) {
		LinkedList<PartRecord> temp = new LinkedList<PartRecord>();
		temp.add(new PartRecord(123123,"tent name",12,4,"assets/img/products/coolTent.png",0));
		
		model.addAttribute("products", temp);
		return "c_orders";
	}

}