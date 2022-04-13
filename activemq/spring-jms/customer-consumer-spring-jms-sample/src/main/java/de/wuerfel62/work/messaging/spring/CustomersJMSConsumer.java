package de.wuerfel62.work.messaging.spring;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class CustomersJMSConsumer {
	
	public CustomersJMSConsumer(CustomersService service) {
		super();
		this.service = service;
	}

	public final CustomersService service;
	
	@JmsListener(destination = "${messaging.queue}")
	public void onMessage(Message<Customer> message) {
		System.out.println(message.getPayload());
		this.service.addCustomer(message.getPayload());
	}
	
}
