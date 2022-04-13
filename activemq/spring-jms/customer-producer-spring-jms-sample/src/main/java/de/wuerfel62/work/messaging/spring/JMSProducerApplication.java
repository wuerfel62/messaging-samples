package de.wuerfel62.work.messaging.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JMSProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JMSProducerApplication.class, args);
	}
}
