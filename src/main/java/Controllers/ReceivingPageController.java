package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Database.DatabaseInterfacer;
import application.App;


@Controller
@RequestMapping({"r_home"})
public class ReceivingPageController {
	private DatabaseInterfacer DBInterfacer;


	@RequestMapping
    public String main(Model model) {
        model.addAttribute("products", DBInterfacer.getAllPartRecords());
        return "r_home";
    }
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
	
}
