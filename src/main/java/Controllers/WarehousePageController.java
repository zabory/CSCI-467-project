package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Database.DatabaseInterfacer;
import Database.Records.OrderRecord;
import Database.Records.PartRecord;
import application.App;

@Controller
@RequestMapping({"w_home"})
public class WarehousePageController {
	private DatabaseInterfacer DBInterfacer;
	
	@RequestMapping
    public String main(Model model) {
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
	public void processOrder(int Oid)
	{
		OrderRecord record = DBInterfacer.getOrderRecord(Oid);
		
		if(record.getAuthorization() == 1)
		{
			// ready to process
			record.setAuthorization(2);
			
			DBInterfacer.update(record);
			// update inventory in DB
			for(Integer key: record.getParts().keySet())
			{
				PartRecord pRecord = DBInterfacer.getPartRecord(key);
				pRecord.setQuantity(pRecord.getQuantity() - record.getParts().get(key));
				DBInterfacer.update(pRecord);
			}
		}
	}
}
