package myRabbirMQ.workQueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import myRabbirMQ.util.ConnectionUtil;

public class Sender {

	private static final String QUEUE_NAME = "test_work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(int i=0;i<50;i++){
			String msg = " hello world "+i+ "..... ";
			channel.basicPublish("",QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(10);
		}
		System.out.println("消息发送成功 .....");
		
		channel.close();
		connection.close();
	}
}
