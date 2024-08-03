package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class Recv {
    private static final String QUEUE_NAME = "hello";
    private static final Logger logger = LoggerFactory.getLogger(Recv.class);

    private final int myNum;

    public Recv(int myNum) {
        this.myNum = myNum;
    }

    public void receive() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("54.180.140.202");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);

        channel.basicConsume(QUEUE_NAME, false, (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            logger.info("{} - received from queue : {}", myNum, message);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        }, consumerTag -> {});
    }
}
