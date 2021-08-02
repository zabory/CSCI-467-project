package Controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Controllers.Changer.CartPart;
import Controllers.Changer.CustomerChanger;
import Controllers.Changer.CustomerRemover;
import Controllers.Changer.LoginChanger;
import Controllers.Changer.ProductChanger;
import Controllers.Changer.ProductRemover;
import Database.DatabaseInterfacer;
import Database.Records.CustomerRecord;
import Database.Records.PartRecord;
import application.App;

@Controller
public class AdminPageController {
	private static double threshold, cost;
	private DatabaseInterfacer DBInterfacer;

	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
		try {
			Scanner in = new Scanner(new File("CostDataFile.dat"));

			threshold = Double.parseDouble(in.nextLine());
			cost = Double.parseDouble(in.nextLine());

			in.close();
		} catch (FileNotFoundException e) {
			
		}
	}
	
	@GetMapping("/login")
	public String login(Model model, @CookieValue(value = "loggedin", defaultValue = "false") boolean loggedIn) {
		if (!loggedIn) {
			model.addAttribute("loginChanger", new LoginChanger()); // assume SomeBean has a property called datePlanted
			model.addAttribute("login_error", false);
			return "login";
		} else {
			updateInfo(model);
			return "a_home";
		}
	}

	@PostMapping("/login")
	public String login(HttpServletResponse response, Model model, @ModelAttribute("loginChanger") LoginChanger t) {

		if (t.getId().equals("admin") && t.getPass().equals("password")) {
			updateInfo(model);
			Cookie cookie = new Cookie("loggedin", "true");
			cookie.setMaxAge(60 * 30); // sets age of cookie to 30 minutes
			response.addCookie(cookie);
			return "a_home";
		}

		model.addAttribute("products", DBInterfacer.getAllPartRecords());
		model.addAttribute("cart", "[]");
		model.addAttribute("d_cart", new LinkedList<CartPart>());

		model.addAttribute("login_error", true);
		return "index";
	}

	@GetMapping("/a_home")
	public String showPageAC(Model model) {
		updateInfo(model);
		return "a_home";
	}

	@PostMapping("/a_home/shippingChange")
	public String shippingChange(Model model, @RequestParam("thres") double thres, @RequestParam("cost") double c) {
		threshold = thres;
		cost = c;
		updateCosts();
		updateInfo(model);

		return "a_home";
	}

	@PostMapping("/removeCus")
	public String remove(Model model, @ModelAttribute("cusRemover") CustomerRemover c) throws SQLException {
		CustomerRecord r = new CustomerRecord();
		r.setId(c.getId());
		DBInterfacer.delete(r);

		updateInfo(model);
		model.addAttribute("cusRemover", c);
		return "a_home";
	}

	@PostMapping("/removeProd")
	public String remove(Model model, @ModelAttribute("prodRemover") ProductRemover c) throws SQLException {
		PartRecord r = new PartRecord();
		r.setNumber(c.getId());
		DBInterfacer.delete(r);

		updateInfo(model);
		model.addAttribute("prodRemover", c);
		return "a_home";
	}

	/**
	 * @return the threshold
	 */
	public static double getThreshold() {
		return threshold;
	}

	/**
	 * @return the cost
	 */
	public static double getCost() {
		return cost;
	}

	/**
	 * @param threshold the threshold to set
	 */
	public static void setThreshold(double threshold) {
		AdminPageController.threshold = threshold;
		updateCosts();
	}

	/**
	 * @param cost the cost to set
	 */
	public static void setCost(double cost) {
		AdminPageController.cost = cost;
		updateCosts();
	}

	/**
	 * Adds everything we need to add to the index model
	 * 
	 * @param model Model of the page
	 */
	public void updateInfo(Model model) {
		model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
		model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
		model.addAttribute("products", DBInterfacer.getAllPartRecords());
		model.addAttribute("threshold", threshold);
		model.addAttribute("cost", cost);
		model.addAttribute("cusChanger", new CustomerChanger()); // assume SomeBean has a property called datePlanted
		model.addAttribute("cusRemover", new CustomerRemover());
		model.addAttribute("ProductChanger", new ProductChanger());
		model.addAttribute("prodRemover", new ProductRemover());
	}
	
	public static void updateCosts() {
		try {
			PrintWriter out = new PrintWriter(new File("CostDataFile.dat"));
			out.println(threshold);
			out.println(cost);
			out.close();
		} catch (FileNotFoundException e) {
			
		}
	}


}
