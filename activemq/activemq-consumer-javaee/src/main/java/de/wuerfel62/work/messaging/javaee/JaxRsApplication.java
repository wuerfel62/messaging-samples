package de.wuerfel62.work.messaging.javaee;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
	info = @Info(
		version = "1",
		title = "Customer Producer API"
		)
	)
@ApplicationPath("api/v1")
public class JaxRsApplication extends Application{

}
