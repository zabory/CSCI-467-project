package Controllers;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Database.DatabaseInterfacer;
import application.App;

@Controller
@RequestMapping({ "/", "/index" })
public class ValidateController {
	
	private DatabaseInterfacer DBInterfacer;
	private String input;
	
	
	@RequestMapping("/")
    public String index(
        @RequestParam(value = "Company ID", required = false) String idNum,
        @RequestParam(value = "password", required = false) String pass,
        Model model
    ) {
        model.addAttribute("id", idNum);
        model.addAttribute("password", pass);
        return "index";
    }
	
	
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
