package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Controllers.Changer.CustomerChanger;
import Controllers.Changer.ProductChanger;
import Database.DatabaseInterfacer;
import application.App;

@Controller
public class AdminPageController {
	private static double threshold = 50, cost = 10;
	private DatabaseInterfacer DBInterfacer;

	
	@GetMapping("/a_home")
	public String showPageAC(Model model) {
		model.addAttribute("cusChanger", new CustomerChanger()); // assume SomeBean has a property called datePlanted

		model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
		model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
		model.addAttribute("products", DBInterfacer.getAllPartRecords());
        model.addAttribute("threshold",threshold);
        model.addAttribute("cost",cost);
        model.addAttribute("ProductChanger", new ProductChanger());
		return "a_home";
	}
	
	@PostMapping("/a_home/shippingChange")
	public String shippingChange(Model model, @RequestParam("thres") double thres, @RequestParam("cost") double c) {
		threshold = thres;
		cost = c;

        model.addAttribute("threshold",threshold);
        model.addAttribute("cost",cost);
        
        model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
        model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
        model.addAttribute("products", DBInterfacer.getAllPartRecords());
		
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
	
}
