package de.wuerfel62.work.messaging.javaee;

import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@ApplicationScoped
@Path("customers")
@Tag(name = "customers")
public class CustomerResource {

	@Resource(lookup = "jms/QueueConnectionFactory")
	ConnectionFactory jmsConnectionFactory;

	@Resource(lookup = "jms/CustomerQueue")
	Queue customerQueue;

	@Inject
	CustomerManager manager;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCustomer(@PathParam("id") long id) {
		return this.manager.findById(id).map(Response::ok).orElse(Response.status(Response.Status.NOT_FOUND)).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendCustomer(Customer customer, @Context UriInfo info) throws JMSException {
		this.manager.addCustomer(customer);
		pushToQueue(customer);
		var location = info.getAbsolutePathBuilder().path(Long.toString(customer.getId())).build();
		return Response.created(location).build();
	}
	
	private void pushToQueue(Customer customer) throws JMSException{
		try (var con = jmsConnectionFactory.createConnection();
				var session = con.createSession(false, Session.AUTO_ACKNOWLEDGE)){
			var message = session.createMapMessage();
			message.setStringProperty("produced-by", "java-ee");
			addToMessage(message, "firstName", customer.getFirstName());
			addToMessage(message, "lastName", customer.getLastName());
			addToMessage(message, "birthDate", customer.getBirthDate(), DateTimeFormatter.ISO_LOCAL_DATE::format);
			session.createProducer(customerQueue).send(message);
		}	
	}
	
	private <T> void addToMessage(MapMessage message, String name, T data, Function<T, String> convert) throws JMSException {
		if (data != null) {
			message.setString(name, convert.apply(data));
		}
	}
	
	private void addToMessage(MapMessage message, String name, String data) throws JMSException {
		this.addToMessage(message, name, data, Function.identity());
	}
}
