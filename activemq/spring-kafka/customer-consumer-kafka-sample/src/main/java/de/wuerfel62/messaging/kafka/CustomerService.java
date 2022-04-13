package de.wuerfel62.messaging.kafka;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CustomerService {
	private final List<Customer> customer = new LinkedList<>();

	public void add(Customer customer) {
		System.out.println("Add customer " + customer);
		this.customer.add(customer);
	}
	
	public List<Customer> getCustomer() {
		return customer;
	}

	
}
