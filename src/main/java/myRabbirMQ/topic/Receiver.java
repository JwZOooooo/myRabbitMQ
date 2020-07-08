package myRabbirMQ.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import myRabbirMQ.util.ConnectionUtil;

public class Receiver {
	
	public static final String EXCHANGE = "test_topic_exchange";
	public static final String QUEUE_NAME = "test_topic_queue1";
	
	public static void main(String[] args) throws IOException, TimeoutException {

		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHANGE, "goods.add");
		
		//同一时间给同一消费者发送prefatchCount条消息
		channel.basicQos(1);
		DefaultConsumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println("receive :"+new String(body,"utf-8"));
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					System.out.println("[1] done");
					
					////手动回执，让队列知道消息已消费完
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			
			}
		};
		channel.basicConsume(QUEUE_NAME, false, consumer);		
		
	}

}
