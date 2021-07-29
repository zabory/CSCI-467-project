package Controllers;

import javax.annotation.PostConstruct;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import Database.DatabaseInterfacer;
import Database.Records.OrderRecord;
import application.App;

@Controller 
@RequestMapping({ "/", "/index"})
public class UserPageController {
	
	private final static String VENDOR_ID = "";
	
	private DatabaseInterfacer DBInterfacer;
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
	
	@RequestMapping
    public String main(Model model) {
		model.addAttribute("products",DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		System.out.println("hi");
        return "index";
    }
	/**
	 * 
	 * @param ccNumber
	 * @param expirDate
	 * @param amount
	 * @param name
	 * @param transID
	 * @return
	 */
	
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
