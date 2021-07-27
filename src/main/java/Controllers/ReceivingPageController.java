package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Database.DatabaseInterfacer;
import Database.Records.PartRecord;
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
	
	/**
	 * Processes order record and updates inventory
	 * @param Oid Order ID
	 */
	public void updateInventory(int pID, int amount){
		
		PartRecord record = DBInterfacer.getPartRecord(pID);
		record.setQuantity(amount);
		DBInterfacer.update(record);
		
	}
}
