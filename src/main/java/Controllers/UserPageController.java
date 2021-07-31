package Controllers;

import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import Controllers.Changer.CartPart;
import Controllers.Changer.ReceivingChanger;
import Controllers.Changer.SearchChanger;
import Database.DatabaseInterfacer;
import Database.Records.PartRecord;
import application.App;
import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

@Controller 
public class UserPageController {
	
	
	private DatabaseInterfacer DBInterfacer;
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

	@RequestMapping({ "/", "/index"})
    public String main(Model model) {
		model.addAttribute("products",DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		model.addAttribute("d_cart",new LinkedList<CartPart>());

		model.addAttribute("searchChanger", new SearchChanger()); // assume SomeBean has a property called datePlanted
		model.addAttribute("login_error",false);

        return "index";
    }
	

	@PostMapping("/search")
	public String showPage(@ModelAttribute("searchChanger") SearchChanger value, Model model) {
		System.out.println(value.getInput());
		
		LinkedList<PartRecord> newList = new LinkedList<PartRecord>(), old = DBInterfacer.getAllPartRecords();
		
		if(value.getInput() == "null") {
			newList = old;
			model.addAttribute("searchChanger", ""); // assume SomeBean has a property called datePlanted
		}else {
			model.addAttribute("searchChanger", value.getCart()); // assume SomeBean has a property called datePlanted
			for(int i=0;i<old.size();i++) {
				if(old.get(i).getDescription().toLowerCase().contains(value.getInput().toLowerCase())) {
					System.out.println(old.get(i).getDescription());
					newList.add(old.get(i));
				}
			}
		}

		model.addAttribute("products",newList);
		model.addAttribute("cart",value.getCart());
		model.addAttribute("login_error",false);
		try {
			model.addAttribute("d_cart",convertJsonCart(new JSONArray(value.getCart())));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "index";
	}

	public LinkedList<CartPart> convertJsonCart(JSONArray a) {
		if(a == null) return new LinkedList<CartPart>();
		// display list for the front end
		LinkedList<CartPart> d_cart = new LinkedList<CartPart>();

		// loop through the cart items
		for (int i = 0; i < a.length(); i++) {
			// Accessor for key values
			Iterator<?> keys;
			try {
				keys = a.getJSONObject(i).keys();

				PartRecord part;

				// Gets all (only one) of the keys + values
				while (keys.hasNext()) {
					String key = (String) keys.next();
					part = DBInterfacer.getPartRecord(Integer.parseInt(key));
					d_cart.add(new CartPart(part.getNumber(), part.getDescription(),
							Integer.parseInt((String) a.getJSONObject(i).get(key))));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return d_cart;
	}
}
