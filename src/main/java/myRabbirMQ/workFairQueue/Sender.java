package myRabbirMQ.workFairQueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import myRabbirMQ.util.ConnectionUtil;

public class Sender {

	private static final String QUEUE_NAME = "test_work_fair_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/**
		 * 每个消费者发送确认消息之前，队列不发送下一个消息到消费者，
		 * 一次只处理一个消息
		 * 限制发送给同一个消费者消息不得超过一条
 		 */
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		
		
		
		
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
