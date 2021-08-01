package Controllers;

import java.sql.SQLException;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import Controllers.Changer.SearchChanger;
import Database.DatabaseInterfacer;
import Database.Records.CustomerRecord;
import Database.Records.PartRecord;
import application.App;

@Controller
public class AdminPageController {
	private static double threshold = 50, cost = 10;
	private DatabaseInterfacer DBInterfacer;

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("loginChanger", new LoginChanger()); // assume SomeBean has a property called datePlanted
		model.addAttribute("login_error",false);
		return "login";
	}
	@PostMapping("/login")
	public String login(Model model, @ModelAttribute("loginChanger") LoginChanger t) {

		if(t.getId().equals("admin") && t.getPass().equals("password")) {
			updateInfo(model);
	        
			return "a_home";
		}
		
		model.addAttribute("products", DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		model.addAttribute("d_cart",new LinkedList<CartPart>());

		model.addAttribute("searchChanger", new SearchChanger()); // assume SomeBean has a property called datePlanted
		model.addAttribute("login_error",true);
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
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
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
	public void updateInfo(Model model) {
		model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
		model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
		model.addAttribute("products", DBInterfacer.getAllPartRecords());
        model.addAttribute("threshold",threshold);
        model.addAttribute("cost",cost);
		model.addAttribute("cusChanger", new CustomerChanger()); // assume SomeBean has a property called datePlanted
        model.addAttribute("cusRemover", new CustomerRemover());
        model.addAttribute("ProductChanger", new ProductChanger());
        model.addAttribute("prodRemover", new ProductRemover());
	}
}
