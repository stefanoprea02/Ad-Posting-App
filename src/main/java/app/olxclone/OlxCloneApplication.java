package app.olxclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class OlxCloneApplication {

	public static void main(String[] args) {
		SpringApplication.run(OlxCloneApplication.class, args);
	}

}