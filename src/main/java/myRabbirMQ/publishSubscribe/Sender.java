package myRabbirMQ.publishSubscribe;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import myRabbirMQ.util.ConnectionUtil;

/**
 * 订阅模式
 * @author 18209
 *
 */
public class Sender {
	public final static String EXCHANGE="test_exchange";
	public static void main(String[] args) throws IOException, TimeoutException {
		
		Connection connection = ConnectionUtil.getConnection();
		Channel channel = connection.createChannel();
		
		//声明交换机
		channel.exchangeDeclare(EXCHANGE, "fanout");
		
		//向交换机发送消息
		String msg = "hello publish subscrible";
		channel.basicPublish(EXCHANGE, "", null,msg.getBytes());
		System.out.println("send : "+msg);
		channel.close();
		connection.close();
		
	}
	
	
}
