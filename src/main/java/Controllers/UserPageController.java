package Controllers;

import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Controllers.Changer.CartPart;
import Database.DatabaseInterfacer;
import Database.Records.PartRecord;
import application.App;

@Controller 
public class UserPageController {
	
	
	private DatabaseInterfacer DBInterfacer;
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

	@RequestMapping({ "/", "/index"})
    public String main(Model model, @CookieValue(value = "searchParam", defaultValue = "") String searchBar) {
		model.addAttribute("products",DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		model.addAttribute("d_cart",new LinkedList<CartPart>());

		model.addAttribute("searchParam", searchBar); // assume SomeBean has a property called datePlanted
		model.addAttribute("login_error",false);
		
        return "index";
    }
	

	@PostMapping("/search")
	public String showPage(@RequestParam("searchParam") String value,@RequestParam("cart") String cart, Model model, HttpServletResponse response) {
		
		LinkedList<PartRecord> newList = new LinkedList<PartRecord>(), old = DBInterfacer.getAllPartRecords();

		Cookie n = new Cookie("searchParam","");
		n.setMaxAge(60 * 30); //sets age of cookie to 30 minutes
        
		if(value == "null") {
			newList = old;
			n.setValue("");
		}else {
			n.setValue(value);
			for(int i=0;i<old.size();i++) {
				if(old.get(i).getDescription().toLowerCase().contains(value.toLowerCase())) {
					newList.add(old.get(i));
				}
			}
		}

        response.addCookie(n);
		model.addAttribute("searchParam",value);
		model.addAttribute("products",newList);
		model.addAttribute("cart",cart);
		model.addAttribute("login_error",false);
		try {
			model.addAttribute("d_cart",convertJsonCart(new JSONArray(cart)));
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
