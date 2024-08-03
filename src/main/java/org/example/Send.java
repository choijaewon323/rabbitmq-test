package org.example;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    private static final String QUEUE_NAME = "hello";
    private static final Logger logger = LoggerFactory.getLogger(Send.class);

    public void send() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("54.180.140.202");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            for (int i = 0; i < 30; i++) {
                String message = "message" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                logger.info("successfully send message");
            }
        } catch (IOException | TimeoutException e){
            logger.info(e.getMessage());
        }
    }
}
