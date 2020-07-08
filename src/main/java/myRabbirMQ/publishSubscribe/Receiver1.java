package myRabbirMQ.publishSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import myRabbirMQ.util.ConnectionUtil;

public class Receiver1 {
	public final static String QUEUE1="test_queue_email";
	public final static String EXCHANGE="test_exchange";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE1, false, false, false, null);
		
		//绑定到交换机上
		channel.queueBind(QUEUE1, EXCHANGE, "");
		
		/**
		 * 使用basicQos方法时，参数为prefetchCount 值，
		 * 告诉RabbitMQ不要在同一时间给一个消费者超过prefetchCount条消息。
		 * 换句话说，只有在消费者空闲的时候会发送下一条信息。
		 */ 
		channel.basicQos(1);
		//定义消费者
		DefaultConsumer consumer1 = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
					System.out.println("[1] recv1 :"+new String(body));
					try {
						Thread.sleep(1*1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}finally {
						channel.basicAck(envelope.getDeliveryTag(),false);
					}
			}
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE1,autoAck, consumer1);
		
	}
}
