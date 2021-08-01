package Controllers;

import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Controllers.Changer.CartPart;
import Controllers.Changer.CustomerChanger;
import Controllers.Changer.ProductChanger;
import Controllers.Changer.ReceivingChanger;
import Controllers.Changer.WarehouseChanger;
import Database.DatabaseInterfacer;
import Database.Records.CustomerRecord;
import Database.Records.OrderRecord;
import Database.Records.PartRecord;
import application.App;
import packing.PackingListGenerator;

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

	@GetMapping("w_home/packingList")
	public ResponseEntity<InputStreamResource> processPackingList(@RequestParam("orderID") String orderID) {
		
		OrderRecord order = DBInterfacer.getOrderRecord(Integer.parseInt(orderID));
		
		LinkedList<PartRecord> parts = new LinkedList<PartRecord>();
		
		for(Integer x : order.getParts().keySet()) {
			parts.add(DBInterfacer.getPartRecord(x));
		}
		
		CustomerRecord r = DBInterfacer.getCustomerRecord(order.getCustomerID());
		ByteArrayInputStream bis = PackingListGenerator.GeneratePackingList(r.getName(), r.getStreet(), r.getCity(), parts, order.getParts());

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline; filename=PackingList.pdf");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
				.body(new InputStreamResource(bis));
	}

	@PostMapping("/a_home/editProduct")
	public String editProduct(@ModelAttribute("ProductChanger") ProductChanger t, Model model) {

		PartRecord record = DBInterfacer.getPartRecord(t.getNumber());

		record.setDescription(t.getDescription());
		record.setPrice(t.getPrice());
		record.setWeight(t.getWeight());
		record.setQuantity(t.getQuantity());

		DBInterfacer.update(record);

		model.addAttribute("orders", DBInterfacer.getAllOrderRecords());
		model.addAttribute("customers", DBInterfacer.getAllCustomerRecords());
		model.addAttribute("products", DBInterfacer.getAllPartRecords());
		model.addAttribute("threshold", AdminPageController.getThreshold());
		model.addAttribute("cost", AdminPageController.getCost());

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
		model.addAttribute("threshold", AdminPageController.getThreshold());
		model.addAttribute("cost", AdminPageController.getCost());

		return "a_home";
	}

	@PostMapping("/add")
	public String addToCart(Model model, @RequestParam("productId") String prodID,
			@RequestParam("newAmount") String newAmount, @RequestParam("cart") String cart,
			 @CookieValue(value = "searchParam", defaultValue = "") String searchInfo) {
		
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
		
		LinkedList<PartRecord> old = DBInterfacer.getAllPartRecords(), newList = new LinkedList<PartRecord>();
		for(int i=0;i<old.size();i++) {
			if(old.get(i).getDescription().toLowerCase().contains(searchInfo.toLowerCase())) {
				newList.add(old.get(i));
			}
		}
		model.addAttribute("products", newList);
		model.addAttribute("searchParam", searchInfo);

		return "index";
	}
	
	
	@PostMapping("/remove")
	public String addToCart(Model model, @RequestParam("productId") String prodID, @RequestParam("cart") String cart) {
		try {
			JSONArray newCart = new JSONArray(), oldCart = new JSONArray(cart);
			
			for(int i=0; i < oldCart.length(); i++) {
				if(!oldCart.getJSONObject(i).names().get(0).equals(prodID)) {
					newCart.put(oldCart.get(i));
				}
			}
			
			model.addAttribute("d_cart", convertJsonCart(newCart));
			model.addAttribute("cart", newCart.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
					d_cart.add(new CartPart(part.getNumber(), part.getDescription(),
							Integer.parseInt((String) a.getJSONObject(i).get(key))));
				}
			} catch (JSONException e) {
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

			EmailController.doSendEmail(DBInterfacer.getCustomerRecord(record.getCustomerID()).getContact(),
					"Welp, we did it.", "Your order is on its way!");

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
