/**
 * TODO
 * 
 */
package com.rabbitmq.service.SingleQueue;

import com.rabbitmq.client.*;
import com.rabbitmq.controller.MainController;

import java.io.IOException;

/**
 * @author jialianqing
 *
 */
public class producer {

  private final static String QUEUE_NAME = "Qone301";
  private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(producer.class);   //log4j 2.x 版本


  public static void initProducer() throws Exception {
    //Producer
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("192.168.1.33");
    factory.setUsername("asdf");
    factory.setPassword("123456");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();
    channel.queueDeclare(QUEUE_NAME, false, false, false, null);

    //Consumer
    Consumer consumer = new DefaultConsumer(channel) {
      @Override
      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        String message = new String(body, "UTF-8");
        System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
        LOGGER.debug(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'"+" %R %A ");
      }
    };
    channel.basicConsume(QUEUE_NAME, consumer);

    //Producer
    for(int i=0;i<10;i++) {
      String message = "OneProducerOneConsumer"+i;
      channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
      System.out.println("P [x] Sent '" + message + "'");
      LOGGER.debug("P [x] Sent '" + message + "'"+" %R %A ");
    }
    //channel.basicConsume("",consumer);
/*
      channel.close();
      connection.close();*/

  }
}
