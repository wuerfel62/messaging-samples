package de.wuerfel62.messaging.kafka;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/customers")
@Controller
public class CustomerController {
	private final KafkaTemplate<String, Customer> template;

	public CustomerController(KafkaTemplate<String, Customer> template) {
		super();
		this.template = template;
	}

	@PostMapping
	public String sendMessage(@RequestParam String name,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthDate) {
		Customer customer = new Customer(name, birthDate);
		this.template.send("customers", customer).completable().thenAccept(System.out::println);
		return "redirect:index.html";
	}

}
