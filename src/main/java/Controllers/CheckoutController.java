package Controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.PostConstruct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import Controllers.Changer.CartPart;
import Database.DatabaseInterfacer;
import Database.Records.CustomerRecord;
import Database.Records.OrderRecord;
import Database.Records.PartRecord;
import application.App;

@Controller
public class CheckoutController {

	private final static String VENDOR_ID = "";
	private DatabaseInterfacer DBInterfacer;

	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}

	@GetMapping("/checkout")
	public String showPageW(Model model) {
		model.addAttribute("cart", "");
		model.addAttribute("d_cart", new LinkedList<CartPart>());

		return "checkout";
	}

	@PostMapping("/checkout")
	public String addToCart(Model model, @RequestParam("cart") String cart) {
		model.addAttribute("cart", cart);
		model.addAttribute("cc_error",false);
		model.addAttribute("cart_total", calculateCost(cart));
		model.addAttribute("shipping_total", calculateShippingCosts(cart));

		try {
			model.addAttribute("d_cart", convertJsonCart(new JSONArray(cart)));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return "checkout";
	}

	@PostMapping("/checkout/final")
	public String checkoutFinal(Model model,
			@RequestParam("name") String name,@RequestParam("street") String street,
			@RequestParam("city") String city,@RequestParam("contact") String contact, @RequestParam("cart") String cart,
			@RequestParam("ccNumber") String ccNumber, @RequestParam("expirDate") String  expirDate) {
		
		model.addAttribute("cart_total", calculateCost(cart));
		model.addAttribute("shipping_total", calculateShippingCosts(cart));
		
		CustomerRecord customer = DBInterfacer.getCustomer(name);
		
		if(customer == null) {
			customer = new CustomerRecord(getOpenCustomerID(), name, city, street, contact);
			DBInterfacer.insert(customer);
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
	    Date date = new Date();
	    HashMap<Integer, Integer> parts = new HashMap<Integer, Integer>();
	    
	    JSONArray partsJSON;
		try {
			partsJSON = new JSONArray(cart);
			
			  for(int i = 0; i < partsJSON.length(); i++) {
			    	try {
						parts.put(Integer.parseInt(partsJSON.getJSONObject(i).names().get(0) + ""), partsJSON.getJSONObject(i).getInt(partsJSON.getJSONObject(i).names().get(0) + ""));
					} catch (NumberFormatException | JSONException e) {
						e.printStackTrace();
					}
			    }
		} catch (JSONException e1) {
			e1.printStackTrace();
		}    
		
		OrderRecord order = new OrderRecord(formatter.format(date), getOpenOrderID(), parts, 1, customer.getId());
		
		double cost = calculateShippingCosts(order) + calculateCost(order);
		
		if(validateCreditCard(ccNumber, expirDate, cost, name, System.currentTimeMillis() + "")) {
			DBInterfacer.insert(order);
		} else {
			model.addAttribute("cc_error",true);
			model.addAttribute("cart",cart);
			try {
				model.addAttribute("d_cart", convertJsonCart(new JSONArray(cart)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return "checkout";
		}
		
		EmailController.doSendEmail(contact, "Your order has been submitted to Bad Car Parts R' Us", "Once we have the inventory, we will send out your order!");

		model.addAttribute("products", DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		model.addAttribute("d_cart",new LinkedList<CartPart>());
		return "index";
	}

	/**
	 * Turns a jsonArray into a linkedList for CartParts
	 * 
	 * @param a JSONArray input json
	 */
	private LinkedList<CartPart> convertJsonCart(JSONArray a) {

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

	private boolean validateCreditCard(String ccNumber, String expirDate, double amount, String name, String transID) {

		String postURL = "http://blitz.cs.niu.edu/CreditCard/";
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		JSONObject body = new JSONObject();

		try {
			body.put("vendor", VENDOR_ID);
			body.put("trans", transID);
			body.put("cc", ccNumber);
			body.put("name", name);
			body.put("amount", amount + "");
			body.put("exp", expirDate);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		HttpEntity<String> request = new HttpEntity<String>(body.toString(), headers);
		JSONObject result;
		try {
			result = new JSONObject(restTemplate.postForObject(postURL, request, String.class));
			return !result.has("errors");
		} catch (RestClientException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private double calculateCost(OrderRecord order) {
		double total = 0;
		
		for(Integer x: order.getParts().keySet()) {
			total += (DBInterfacer.getPartRecord(x).getPrice() * order.getParts().get(x));
		}
		
		return total;
	}
	
	private double calculateCost(String cart) {
		double total = 0;
		
		HashMap<Integer, Integer> parts = new HashMap<Integer, Integer>();
	    
	    JSONArray partsJSON;
		try {
			partsJSON = new JSONArray(cart);
			
			  for(int i = 0; i < partsJSON.length(); i++) {
			    	try {
						parts.put(Integer.parseInt(partsJSON.getJSONObject(i).names().get(0) + ""), partsJSON.getJSONObject(i).getInt(partsJSON.getJSONObject(i).names().get(0) + ""));
					} catch (NumberFormatException | JSONException e) {
						e.printStackTrace();
					}
			    }
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		for(Integer x : parts.keySet()) {
			total += (DBInterfacer.getPartRecord(x).getPrice() * parts.get(x));
		}
		
		return total;
	}
	
	private int getOpenCustomerID() {
		int id = 1;
		boolean found = DBInterfacer.getAllCustomerRecords().size() == 0;
		while(!found) {
			for(CustomerRecord r : DBInterfacer.getAllCustomerRecords()) {
				if(r.getId() == id) {
					found = true;
					break;
				}
			}
			if(found) {
				id++;
				found = false;
			} else {
				break;
			}
		}
		
		return id;
	}
	
	private int getOpenOrderID() {
		int id = 1;
		boolean found = DBInterfacer.getAllOrderRecords().size() == 0;
		while(!found) {
			for(OrderRecord r : DBInterfacer.getAllOrderRecords()) {
				if(r.getID() == id) {
					found = true;
					break;
				}
			}
			if(found) {
				id++;
				found = false;
			} else {
				break;
			}
		}
		
		return id;
	}

	private double calculateShippingCosts(OrderRecord order) {
		return ((int) (Double.parseDouble(order.getOrderWeight()) / AdminPageController.getThreshold()) + 1) * AdminPageController.getCost();
	}
	
	private double calculateShippingCosts(String cart) {
		OrderRecord order = new OrderRecord();
		HashMap<Integer, Integer> parts = new HashMap<Integer, Integer>();
		JSONArray partsJSON;
		try {
			partsJSON = new JSONArray(cart);
			  for(int i = 0; i < partsJSON.length(); i++) {
			    	try {
						parts.put(Integer.parseInt(partsJSON.getJSONObject(i).names().get(0) + ""), partsJSON.getJSONObject(i).getInt(partsJSON.getJSONObject(i).names().get(0) + ""));
					} catch (NumberFormatException | JSONException e) {
						e.printStackTrace();
					}
			    }
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		order.setParts(parts);
		
		return calculateShippingCosts(order);
	}
}
