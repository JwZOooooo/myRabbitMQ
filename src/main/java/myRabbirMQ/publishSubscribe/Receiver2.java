package myRabbirMQ.publishSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import myRabbirMQ.util.ConnectionUtil;

public class Receiver2 {
	public final static String QUEUE2="test_queue_message";
	public final static String EXCHANGE="test_exchange";

	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明队列
		channel.queueDeclare(QUEUE2, false, false, false, null);
		
		//绑定到交换机上
		channel.queueBind(QUEUE2, EXCHANGE, "");
		channel.basicQos(1);
		//定义消费者
		DefaultConsumer consumer1 = new DefaultConsumer(channel){
			
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
					System.out.println("[2] recv2 :"+new String(body));
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
		channel.basicConsume(QUEUE2,true, consumer1);
		
	}
}
