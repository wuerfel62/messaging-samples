package de.wuerfel62.work.messaging.spring;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JMSConfig {
	
	@Bean
	MessageConverter defaultMessageConverter(final ObjectMapper objectMapper) {
		var result = new MappingJackson2MessageConverter() {
			@Override
			protected JavaType getJavaTypeForMessage(Message message) throws JMSException{
				return objectMapper.constructType(Customer.class);
			}
		};
		result.setTargetType(MessageType.TEXT);
        result.setObjectMapper(objectMapper);
        result.setTypeIdMappings(Map.of("customer", Customer.class));
        result.setTypeIdPropertyName("customer");
        return result;
	}
}
