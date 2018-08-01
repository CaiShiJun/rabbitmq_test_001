/**
 * TODO
 * 
 */
package com.rabbitmq.service.routing;

import com.rabbitmq.client.*;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author jialianqing
 *
 */
public class RoutingSendDirect {
    @Resource
    private static DataSource druidDSMysql;
    static String insertsql = "insert into testUser(userName,pwd) values(?,?)";
    private static final String EXCHANGE_NAME = "Edirect";
 	private static final String routingKeys = "info";
    private static final String queue = "queue1";
    @ResponseBody
    public static void initRoutingProducer(int para) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.1.33");
/*        factory.setUsername("asdf");
        factory.setPassword("123456");*/
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String queueName="";
        if(para==0){
            channel.queueDeclare(queue, false, false, false, null);
        }else {
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");
            queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, routingKeys);
        }

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
            }
        };
        if(para==0){
            channel.basicConsume(queue, true, consumer);
        }else{
            channel.basicConsume(queueName, true, consumer);
        }
        if (para == 1) {
                for(int i=0;i<10;i++) {
                    String message = "Send the message level:" + routingKeys;
                    channel.basicPublish(EXCHANGE_NAME, routingKeys, null,message.getBytes());
                    System.out.println(" [Exchange is not null] Sent '" + routingKeys + "':'" + message + "'");
                }
                channel.exchangeDelete(EXCHANGE_NAME);
            } else {
                for (int i = 0; i < 10; i++) {
                    String message = "Send the message level:" + queue;
                    channel.basicPublish("", queue, null, message.getBytes());
                    System.out.println(" [Exchange is null] Sent '" + queue + "':'" + message + "'");
                }
                channel.queueDelete(queue);
            }
/*        channel.close();
        connection.close();*/
    }
}