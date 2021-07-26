package application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Database.DatabaseInterfacer;

@SpringBootApplication(scanBasePackages = {"Controllers"})
public class App {
	
	private static DatabaseInterfacer DBInterfacer;

	public static void main(String[] args) {
		DBInterfacer = new DatabaseInterfacer();
		SpringApplication.run(App.class, args);
	}
	
	public static DatabaseInterfacer getDatabaseInterfacer() {
		return DBInterfacer;
	}

}
