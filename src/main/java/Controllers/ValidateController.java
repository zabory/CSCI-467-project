package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import Database.DatabaseInterfacer;
import application.App;

@Controller
@RequestMapping({ "/", "/index" })
public class ValidateController {
	
	private DatabaseInterfacer DBInterfacer;
	private String input;
	
	@GetMapping
    public String main(Model model) {
        model.addAttribute("user", input);
        return "index";
    }

    @PostMapping
    public String save(Model model) {
        model.addAttribute("user", input);
        return "validate";
    }
    
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
}
