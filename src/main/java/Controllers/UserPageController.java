package Controllers;

import java.util.LinkedList;

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

import Controllers.Changer.CartPart;
import Database.DatabaseInterfacer;
import Database.Records.OrderRecord;
import application.App;

@Controller 
@RequestMapping({ "/", "/index"})
public class UserPageController {
	
	
	private DatabaseInterfacer DBInterfacer;
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
	
	@RequestMapping
    public String main(Model model) {
		model.addAttribute("products",DBInterfacer.getAllPartRecords());
		model.addAttribute("cart","[]");
		model.addAttribute("d_cart",new LinkedList<CartPart>());
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
	
	
	
}
