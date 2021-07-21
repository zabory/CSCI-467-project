package Controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import Database.DatabaseInterfacer;
import application.App;

@Controller 
public class UserPageController {
	
	private DatabaseInterfacer DBInterfacer;
	
	@PostConstruct
	public void initialize() {
		DBInterfacer = App.getDatabaseInterfacer();
	}
	
}
