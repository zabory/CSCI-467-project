package Controllers;

import java.util.LinkedList;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import Controllers.Changer.CartPart;
import Database.DatabaseInterfacer;
import application.App;

@Controller 
@RequestMapping({ "/", "/index"})
public class UserPageController {
	
	
	private DatabaseInterfacer DBInterfacer;
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
	
	@RequestMapping
    public String main(Model model) {
		model.addAttribute("products",DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		model.addAttribute("d_cart",new LinkedList<CartPart>());
        return "index";
    }
	
}
