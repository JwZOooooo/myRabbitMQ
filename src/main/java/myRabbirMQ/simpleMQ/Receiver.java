package myRabbirMQ.simpleMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import myRabbirMQ.util.ConnectionUtil;

public class Receiver {
	private static final String queue = "test_simple_queue";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue, false, false, false, null);

		DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				
				String msg = new String(body,"utf-8");
				System.out.println("receive msg :  "+msg);
			}
		};
		//¼àÌý¶ÓÁÐ
		channel.basicConsume(queue, true, consumer);
		
	}
}
