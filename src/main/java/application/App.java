package application;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Database.DatabaseInterfacer;

@SpringBootApplication(scanBasePackages = {"Controllers"})
public class App {
	
	private static DatabaseInterfacer DBInterfacer;

	public static void main(String[] args) {
		DBInterfacer = new DatabaseInterfacer();
		//AnnotationConfigApplicationContext c = new AnnotationConfigApplicationContext();
		SpringApplication s = new SpringApplication(App.class);
		Properties config = new Properties();
		config.setProperty("spring.main.banner-mode", "off");
		config.setProperty("logging.level.root", "INFO");
		config.setProperty("server.port", "85");
		config.setProperty("sam", "is_cool");
		
		s.setDefaultProperties(config);
		s.run(args);
	}
	
	public static DatabaseInterfacer getDatabaseInterfacer() {
		return DBInterfacer;
	}

}
