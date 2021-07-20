package Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Database.Records.CustomerRecord;

@Controller
@RequestMapping({ "/", "/index" })
public class ValidateController {
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
}
