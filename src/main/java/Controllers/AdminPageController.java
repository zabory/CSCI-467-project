package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Database.DatabaseInterfacer;
import Database.Records.PartRecord;
import application.App;

@Controller
public class AdminPageController {

	private DatabaseInterfacer DBInterfacer;

	@GetMapping("/ProductEditing")
	public String greetingForm(Model model) {

		return "ProductEditing";
	}

	@PostMapping("/ProductEditing")
	public String greetingSubmit(@ModelAttribute PartRecord record, Model model) {
		model.addAttribute("record", record);

		return "results";
	}

	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

}
