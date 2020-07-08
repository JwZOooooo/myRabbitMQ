package myRabbirMQ.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import myRabbirMQ.util.ConnectionUtil;

public class Sender {

	public static final String EXCHANGE = "test_topic_exchange"; 
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		//ÉùÃ÷exchange topic
		channel.exchangeDeclare(EXCHANGE, "topic");
		String msg = "ÉÌÆ·! ";
		channel.basicPublish(EXCHANGE, "goods.add", null, msg.getBytes());
		System.out.println("send "+ msg);
		channel.close();
		connection.close();
	}
}
