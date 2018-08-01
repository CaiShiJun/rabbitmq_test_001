/**
 * TODO
 * 
 */
package com.rabbitmq.service.publish;

/**
 * @author hushuang
 *
 */

import com.rabbitmq.client.*;

import java.io.IOException;

public class EmitLog {

    private static final String EXCHANGE_NAME = "EPub";

    public static void initPubProducer() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.33");
/*        factory.setUsername("asdf");
        factory.setPassword("123456");*/
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(queueName,true, consumer);
        for(int i = 0 ; i < 10; i++){
            String message = "Testfanout! " + i;
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
        channel.exchangeDelete(EXCHANGE_NAME);
        channel.close();
        connection.close();
    }
}
