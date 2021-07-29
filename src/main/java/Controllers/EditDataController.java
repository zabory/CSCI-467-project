package Controllers;

import javax.annotation.PostConstruct;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import Controllers.Changer.CartChanger;
import Controllers.Changer.CustomerChanger;
import Controllers.Changer.ReceivingChanger;
import Controllers.Changer.WarehouseChanger;
import Database.DatabaseInterfacer;
import Database.Records.CustomerRecord;
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
    

	@GetMapping("/a_home")
    public String showPageAC(Model model) {
        model.addAttribute("cusChanger", new CustomerChanger()); //assume SomeBean has a property called datePlanted

        model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
        model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
        model.addAttribute("products", DBInterfacer.getAllPartRecords());
        return "a_home";
    }

    @PostMapping("/a_home")
    public String showPageAC(@ModelAttribute("cusChanger") CustomerChanger bean, Model model) {
    	
    	CustomerRecord r = DBInterfacer.getCustomerRecord(bean.getId());
		r.setName(bean.getName());
		r.setCity(bean.getCity());
		r.setStreet(bean.getStreet());
		r.setContact(bean.getContact());
		
		DBInterfacer.update(r);

        model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
        model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
        model.addAttribute("products", DBInterfacer.getAllPartRecords());

        
        return "a_home";
    }  
    
    
//    @PostMapping("/add")
//    public String showPageW(@ModelAttribute("newCart") CartChanger bean, @RequestParam("oldCart") String s, Model model) {
//    	
//    	System.out.println(bean);
//    	
//        model.addAttribute("products", DBInterfacer.getAllPartRecords());
//		model.addAttribute("newCart", new CartChanger("123:1;"));
//
//		model.addAttribute("oldCart", new CartChanger("123:1;"));
//        return "index";
//    }  
    
    
    @PostMapping("/add")
    public String addToCart(Model model, @RequestParam("productId") String prodID, @RequestParam("newAmount") String newAmount,  @RequestParam("cart") String cart) {
    	
    	cart += "1";
    	System.out.println("cart: " + cart + "ID: " + prodID + "\tAmount:" + newAmount);

        model.addAttribute("products",DBInterfacer.getAllPartRecords());
        model.addAttribute("cart",cart);
        
        return "index";
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
