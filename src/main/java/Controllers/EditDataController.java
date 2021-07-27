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

import Database.Records.ReceivingChanger;


@Controller
public class EditDataController {
	
	@GetMapping("/r_home/change")
    public String showPage(Model model) {
        model.addAttribute("countChanger", new ReceivingChanger()); //assume SomeBean has a property called datePlanted
        return "r_home/change";
    }

    @PostMapping("/r_home/change")
    public String showPage(@ModelAttribute("countChanger") ReceivingChanger bean) {
    	
        System.out.println("Amount: " + bean.getNewAmount()); //in reality, you'd use a logger instead :)
        return "r_home";
    }  

}
