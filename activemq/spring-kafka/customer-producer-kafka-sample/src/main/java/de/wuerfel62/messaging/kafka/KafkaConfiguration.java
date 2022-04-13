package de.wuerfel62.messaging.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfiguration {

	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("customers").partitions(2).replicas(1).build();
	}
}
