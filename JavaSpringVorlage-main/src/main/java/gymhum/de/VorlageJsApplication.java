package gymhum.de;

import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VorlageJsApplication {

	
	public static void main(String[] args) throws SQLException {
		DatabaseController db = new DatabaseController();
		db.createTable();
		SpringApplication.run(VorlageJsApplication.class, args);

	}

}
