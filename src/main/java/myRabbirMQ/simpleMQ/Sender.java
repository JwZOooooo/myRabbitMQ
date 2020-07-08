package myRabbirMQ.simpleMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import myRabbirMQ.util.ConnectionUtil;

public class Sender {

	private static final String queue = "test_simple_queue";

	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtil.getConnection();
		// 创建通道
		Channel channel = connection.createChannel();
		channel.queueDeclare(queue, false, false, false, null);
		String msg = "hello simple queue4";

		channel.basicPublish("", queue, null, msg.getBytes());
		System.out.println("send msg : " + msg);
		channel.close();
		connection.close();

	}
}
