package de.wuerfel62.work.messaging.javaee;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Function;

import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;

@MessageDriven(name = "CustomerMDB")
public class CustomerListener {

	@Inject
	CustomerManager manager;
	
	public void onMessage(Message message) throws JMSException {
		if(message instanceof MapMessage) {
			var map = (MapMessage) message;
			Customer customer = new Customer();
			getFromMessage(map, "firstName").ifPresent(customer::setFirstName);
			getFromMessage(map, "lastName").ifPresent(customer::setLastName);
			getFromMessage(map, "birthDate", s -> LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE)).ifPresent(customer::setBirthDate);
			this.manager.addCustomer(customer);
		}
	}
	
	private <T> Optional<T> getFromMessage(MapMessage message, String name, Function<String, T> convert) throws JMSException{
		return Optional.ofNullable(message.getString(name)).map(convert);
	}
	
	private Optional<String> getFromMessage(MapMessage message, String name) throws JMSException {
		return this.getFromMessage(message, name, Function.identity());
	}
}
