/**
 * TODO
 * 
 */
package com.rabbitmq.service.topic;

/**
 * @author hushuang
 *
 */

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TopicSend {

	private static final String EXCHANGE_NAME = "Etopic3.0";

	public static void initTopic(int para) throws IOException, TimeoutException {

		ConnectionFactory factory;
		Connection connection;
		Channel channel = null;
		try {
			factory = new ConnectionFactory();
			factory.setHost("192.168.1.33");
			//factory.setUsername("asdf");
			//factory.setPassword("123456");
			connection= factory.newConnection();
			channel = connection.createChannel();
		}catch (Exception e){
			e.printStackTrace();
		}
		String routingKeys = "*.orange.*";

		//Consumer
		String queueName="";
		if(para==0){
				channel.queueDeclare(routingKeys, false, false, false, null);
		}else{
			channel.exchangeDeclare(EXCHANGE_NAME, "topic");
			queueName = channel.queueDeclare().getQueue();
			channel.queueBind(queueName, EXCHANGE_NAME, routingKeys);
		}

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("ReceiveLogsTopic1 [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
			}
		};
		if(para==0){
				channel.basicConsume(routingKeys, true, consumer);
		}else{
			channel.basicConsume(queueName, true, consumer);
		}

		//Producer
				if (para == 1) {
					for(int i = 0 ; i < 10; i++) {
						String message = "Send the message level:" + routingKeys;
						channel.basicPublish(EXCHANGE_NAME, routingKeys, null, message.getBytes());
						System.out.println(" [Exchange is not null] Sent '" + routingKeys + "':'" + message + "'");
					}
					channel.exchangeDelete(EXCHANGE_NAME);
				} else {
					for (int i = 0; i < 10; i++) {
						String message = "Send the message level:" + routingKeys;
						channel.basicPublish("", routingKeys, null, message.getBytes());
						System.out.println(" [Exchange is null] Sent '" + routingKeys + "':'" + message + "'");
					}
					channel.queueDelete(routingKeys);
				}

		}
			}