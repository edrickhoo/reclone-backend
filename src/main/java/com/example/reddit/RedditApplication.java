package com.example.reddit;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class RedditApplication {

	public static void main(String[] args) {
		Dotenv.configure().load();
		SpringApplication.run(RedditApplication.class, args);
	}

}
