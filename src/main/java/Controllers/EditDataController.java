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
import Database.Records.OrderRecord;
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
        try {
        	updateInventory(Integer.parseInt(bean.getProductId()), Integer.parseInt(bean.getNewAmount()));
        }catch(Exception e) {
        }

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
    	
    	try {
        	processOrder(bean.getOrderID());
        }catch(Exception e) {
        	
        }

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
	
	/**
	 * Processes order record and updates inventory
	 * @param Oid Order ID
	 */
	private void processOrder(int Oid){
		
		OrderRecord record = DBInterfacer.getOrderRecord(Oid);
		
		if(record.getAuthorization() == 1){
			// ready to process
			record.setAuthorization(2);
			
			DBInterfacer.update(record);
			// update inventory in DB
			for(Integer key: record.getParts().keySet()){
				PartRecord pRecord = DBInterfacer.getPartRecord(key);
				pRecord.setQuantity(pRecord.getQuantity() - record.getParts().get(key));
				DBInterfacer.update(pRecord);
			}
		}
	}
}
