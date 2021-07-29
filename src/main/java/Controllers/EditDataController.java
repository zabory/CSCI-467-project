package Controllers;

import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Controllers.Changer.CartPart;
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
		model.addAttribute("countChanger", new ReceivingChanger()); // assume SomeBean has a property called datePlanted

		return "r_home/change";
	}

	@PostMapping("/r_home/change")
	public String showPage(@ModelAttribute("countChanger") ReceivingChanger bean, Model model) {
		try {
			updateInventory(Integer.parseInt(bean.getProductId()), Integer.parseInt(bean.getNewAmount()));
		} catch (Exception e) {
		}

		model.addAttribute("products", DBInterfacer.getAllPartRecords());
		return "r_home";
	}

	@GetMapping("/w_home/change")
	public String showPageW(Model model) {
		model.addAttribute("countChanger", new WarehouseChanger()); // assume SomeBean has a property called datePlanted

		return "w_home/change";
	}

	@PostMapping("/w_home/change")
	public String showPageW(@ModelAttribute("countChanger") WarehouseChanger bean, Model model) {

		try {
			processOrder(bean.getOrderID());
		} catch (Exception e) {

		}

		model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
		return "w_home";
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

	@PostMapping("/add")
	public String addToCart(Model model, @RequestParam("productId") String prodID,
			@RequestParam("newAmount") String newAmount, @RequestParam("cart") String cart) {

		try {
			JSONArray cartJSON = new JSONArray(cart);

			LinkedList<JSONObject> cartList = new LinkedList<JSONObject>();

			// load it into a linked list cuz frick arrays
			for (int i = 0; i < cartJSON.length(); i++) {
				cartList.add(cartJSON.getJSONObject(i));
			}

			// this is the new cart
			JSONArray newCartList = new JSONArray();

			boolean found = false;
			// go through the old cart if it had stuff in it
			if (cartList.size() > 0) {
				for (JSONObject j : cartList) {
					// if the old cart has this product ID, overwrite the new cart
					if (j.has(prodID)) {
						JSONObject newPart = new JSONObject();
						newPart.put(prodID, newAmount);
						newCartList.put(newPart);
						found = true;
					} else {
						// otherwise add the old thing to the new cart
						newCartList.put(j); // dont think so
					}
				}
			}

			if (!found) {
				JSONObject newPart = new JSONObject();
				newPart.put(prodID, newAmount);
				newCartList.put(newPart);
			}
			model.addAttribute("cart", newCartList.toString());
			model.addAttribute("d_cart", convertJsonCart(newCartList));

		} catch (JSONException e) {
			e.printStackTrace();
			model.addAttribute("cart", "");
		}

		model.addAttribute("products", DBInterfacer.getAllPartRecords());

		return "index";
	}

	/**
	 * Turns a jsonArray into a linkedList for CartParts
	 * 
	 * @param a JSONArray input json
	 */
	public LinkedList<CartPart> convertJsonCart(JSONArray a) {

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
					d_cart.add(new CartPart(part.getDescription(),
							Integer.parseInt((String) a.getJSONObject(i).get(key))));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return d_cart;
	}

	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

	/**
	 * Processes order record and updates inventory
	 * 
	 * @param Oid Order ID
	 */
	public void updateInventory(int pID, int amount) {

		PartRecord record = DBInterfacer.getPartRecord(pID);
		record.setQuantity(amount);
		DBInterfacer.update(record);

	}

	/**
	 * Processes order record and updates inventory
	 * 
	 * @param Oid Order ID
	 */
	private void processOrder(int Oid) {

		OrderRecord record = DBInterfacer.getOrderRecord(Oid);

		if (record.getAuthorization() == 1) {
			// ready to process
			record.setAuthorization(2);
			
			EmailController.doSendEmail(DBInterfacer.getCustomerRecord(record.getCustomerID()).getContact(), "Welp, we did it.", "Its coming.");

			DBInterfacer.update(record);
			// update inventory in DB
			for (Integer key : record.getParts().keySet()) {
				PartRecord pRecord = DBInterfacer.getPartRecord(key);
				pRecord.setQuantity(pRecord.getQuantity() - record.getParts().get(key));
				DBInterfacer.update(pRecord);
			}
		}
	}
}
