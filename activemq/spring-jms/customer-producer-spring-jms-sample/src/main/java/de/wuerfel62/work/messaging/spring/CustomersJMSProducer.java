package de.wuerfel62.work.messaging.spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class CustomersJMSProducer {

	private final JmsTemplate template;
	private final String queue;
	
	public CustomersJMSProducer(JmsTemplate template, @Value("${messaging.queue}") String queue) {
		super();
		this.template = template;
		this.queue = queue;
	}

	public void send(Customer customer) {
		this.template.convertAndSend(this.queue, customer);
	}
}
