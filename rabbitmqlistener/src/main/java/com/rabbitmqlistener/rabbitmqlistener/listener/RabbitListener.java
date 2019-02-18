package com.rabbitmqlistener.rabbitmqlistener.listener;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

public class RabbitListener implements MessageListener{

	@Override
	public void onMessage(Message message) {
		System.out.println("Message Receiver*****" + new String(message.getBody()));
	}

}
