package myRabbirMQ.workFairQueue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import myRabbirMQ.util.ConnectionUtil;

public class Receiver2 {
	private static final String QUEUE_NAME = "test_work_fair_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		//声明队列
		channel.queueDeclare(QUEUE_NAME,false,false,false,null);
		channel.basicQos(1);

		//定义消费者
		DefaultConsumer consumer2 = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
					System.out.println("[2] recv2 :"+new String(body));
					try {
						Thread.sleep(2*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						//手动回执，让队列知道消息已消费完
						channel.basicAck(envelope.getDeliveryTag(), false);
					}
			}
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME,autoAck, consumer2);
		
		
	}
	
}
