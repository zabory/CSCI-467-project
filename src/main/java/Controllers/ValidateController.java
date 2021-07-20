package Controllers;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import Database.Records.UserRecord;

@Controller
public class ValidateController {
	
	@RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String submit(@ModelAttribute("user")UserRecord user, 
      BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            return "error";
        }
        
        return "c_orders";
    }
}
