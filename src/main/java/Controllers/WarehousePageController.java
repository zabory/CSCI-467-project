package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Database.DatabaseInterfacer;
import application.App;

@Controller
@RequestMapping({"w_home"})
public class WarehousePageController {
	private DatabaseInterfacer DBInterfacer;
	
	@RequestMapping
    public String main(Model model) {
        model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
        return "w_home";
    }
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
}
