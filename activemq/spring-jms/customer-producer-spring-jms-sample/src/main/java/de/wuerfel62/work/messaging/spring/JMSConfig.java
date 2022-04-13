package de.wuerfel62.work.messaging.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JMSConfig {
	
	@Bean
	MessageConverter defaultMessageConverter(ObjectMapper objectMapper) {
		var result = new MappingJackson2MessageConverter();
		result.setTargetType(MessageType.TEXT);
		result.setObjectMapper(objectMapper);
		return result;
	}
}
