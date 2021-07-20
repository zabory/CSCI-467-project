package Controllers;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Database.DatabaseInterfacer;
import Database.Records.PartRecord;

@Controller
public class AdminPageController {
	
	  @GetMapping("/ProductEditing")
	  public String greetingForm(Model model) {
	    model.addAttribute("record", new PartRecord());
	    return "ProductEditing";
	  }

	  @PostMapping("/ProductEditing")
	  public String greetingSubmit(@ModelAttribute PartRecord record, Model model) {
	    model.addAttribute("record", record);
	    
	    try {
			DatabaseInterfacer db = new DatabaseInterfacer();
			// TODO execute the query 
			db.saveClose();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    
	    return "results";
	  }

}
