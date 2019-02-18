package com.rabbitmqlistener.rabbitmqlistener.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmqlistener.rabbitmqlistener.listener.RabbitListener;


@Configuration
public class RabbitMqConfig {

	private static final String MY_QUEUE = "MyQueue";
	
	@Bean
	Queue myQueue(){
		return new Queue(MY_QUEUE, true);
	}
	
	@Bean
	Queue my2ndQueue(){
		return QueueBuilder.durable("SecondWayOfQueue")
				.autoDelete()
				.exclusive()
				.build();
	}
	
	@Bean
	Exchange exchange() {
		return new TopicExchange("ExampleOneExchange");
	}
	
	@Bean
	Exchange exchange2() {
		return ExchangeBuilder.directExchange("NewDirectExchange")
				.autoDelete()
				.internal()
				.build();
	}
	
	@Bean
	Exchange exchangeTopic() {
		return ExchangeBuilder.topicExchange("NewTopicExchange")
				.autoDelete()
				.internal()
				.build();
	}
	
	
	@Bean
	Binding binding() {
		return BindingBuilder.bind(myQueue())
				.to(exchangeTopic())
				.with("bind")
				.noargs();
	}
	
	@Bean
	Exchange exchangeFanOut() {
		return ExchangeBuilder.fanoutExchange("NewFanOutExchange")
				.autoDelete()
				.internal()
				.build();
	}
	
	@Bean
	Exchange exchangeHeader() {
		return ExchangeBuilder.headersExchange("NewHeaderExchange")
				.autoDelete()
				.internal()
				.build();
	}
	
	@Bean
	ConnectionFactory connection() {
		CachingConnectionFactory connect = new CachingConnectionFactory("192.168.99.100");
		connect.setUsername("guest");
		connect.setPassword("guest");
		return connect;
	}
	
	@Bean
	MessageListenerContainer container() {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connection());
		container.setQueues(myQueue());
		container.setMessageListener(new RabbitListener());
		return container;
	}
	
	
}
