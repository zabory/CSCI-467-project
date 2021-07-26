package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Database.DatabaseInterfacer;
import Database.Records.PartRecord;
import application.App;

@Controller
@RequestMapping({ "/a_home" })
public class AdminPageController {

	private DatabaseInterfacer DBInterfacer;

	@RequestMapping
    public String main(Model model) {
        model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
        model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
        model.addAttribute("products", DBInterfacer.getAllPartRecords());
        return "a_home";
    }
	
	@GetMapping("/ProductEditing")
	public String greetingForm(Model model) {

		return "ProductEditing";
	}

	@PostMapping("/ProductEditing")
	public String greetingSubmit(@ModelAttribute PartRecord record, Model model) {

		return "results";
	}

	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

}
