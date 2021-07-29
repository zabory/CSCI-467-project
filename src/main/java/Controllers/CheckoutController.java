package Controllers;

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
		
		try {
			model.addAttribute("d_cart", convertJsonCart(new JSONArray(cart)));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return "checkout";
	}
	
	

	@PostMapping("/checkout/final")
	public String checkoutFinal(Model model,
			@RequestParam("name") String name,@RequestParam("street") String street,
			@RequestParam("city") String city,@RequestParam("contact") String contact, @RequestParam("cart") String cart,
			@RequestParam("ccNumber") String ccNumber, @RequestParam("expirDate") String  expirDate) {
		System.out.println(name + " " + street + " " + city + " " + contact);
		System.out.println(cart);
		System.out.println(ccNumber + " " + expirDate);
		
		return "checkout";
	}
	/**
	 * Turns a jsonArray into a linkedList for CartParts
	 * 
	 * @param a JSONArray input json
	 */
	private LinkedList<CartPart> convertJsonCart(JSONArray a){

		// display list for the front end 
		LinkedList<CartPart> d_cart = new LinkedList<CartPart>();
		
		// loop through the cart items
		for (int i = 0; i < a.length(); i++) {
			//Accessor for key values
			Iterator<?> keys;
			try {
				keys = a.getJSONObject(i).keys();
				
				PartRecord part;
				
				//Gets all (only one) of the keys + values
				while( keys.hasNext() ) {
				    String key = (String) keys.next();
				    part = DBInterfacer.getPartRecord(Integer.parseInt(key));
					d_cart.add(new CartPart(part.getDescription(),Integer.parseInt((String)a.getJSONObject(i).get(key))));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
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
	
	private double calculateShippingCosts(int Oid)
	{
		final double baseShipPerPound = 20.00;
		OrderRecord record = DBInterfacer.getOrderRecord(Oid);
		return ((int)(Double.parseDouble(record.getOrderWeight()) / 50) + 1)*baseShipPerPound;
		
	}
}
