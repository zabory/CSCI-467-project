package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Database.DatabaseInterfacer;

@SpringBootApplication(scanBasePackages = {"Controllers"})
public class App {
	
	private static DatabaseInterfacer DBInterfacer;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		DBInterfacer = new DatabaseInterfacer();
	}
	
	public static DatabaseInterfacer getDatabaseInterfacer() {
		return DBInterfacer;
	}

}
