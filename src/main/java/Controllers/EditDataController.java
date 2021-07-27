package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import Controllers.Changer.ReceivingChanger;
import Controllers.Changer.WarehouseChanger;
import Database.DatabaseInterfacer;
import Database.Records.PartRecord;
import application.App;


@Controller
public class EditDataController {
	
	private DatabaseInterfacer DBInterfacer;

	@GetMapping("/r_home/change")
    public String showPageR(Model model) {
        model.addAttribute("countChanger", new ReceivingChanger()); //assume SomeBean has a property called datePlanted
        
        return "r_home/change";
    }

    @PostMapping("/r_home/change")
    public String showPage(@ModelAttribute("countChanger") ReceivingChanger bean, Model model) {
        
      updateInventory(Integer.parseInt(bean.getProductId()), Integer.parseInt(bean.getNewAmount()));

        model.addAttribute("products", DBInterfacer.getAllPartRecords());
        return "r_home";
    }  


	@GetMapping("/w_home/change")
    public String showPageW(Model model) {
        model.addAttribute("countChanger", new WarehouseChanger()); //assume SomeBean has a property called datePlanted
        
        return "w_home/change";
    }

    @PostMapping("/w_home/change")
    public String showPageW(@ModelAttribute("countChanger") WarehouseChanger bean, Model model) {
    	
        System.out.println("Amount: " + bean.getProductID()); //in reality, you'd use a logger instead :)

        model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
        return "w_home";
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
