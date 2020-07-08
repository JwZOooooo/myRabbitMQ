package myRabbirMQ.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 获取MQ的连接
 *
 */
public class ConnectionUtil {

	public static Connection getConnection() throws IOException, TimeoutException{
		
		//定义一个连接工厂
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		//设置服务地址
		connectionFactory.setHost("127.0.0.1");
		
		//设置端口 AMQP 5672
		connectionFactory.setPort(5672);
		
		//设置vhost
		connectionFactory.setVirtualHost("/vhost1");
		
		//用户名
		connectionFactory.setUsername("admin");
		//密码
		connectionFactory.setPassword("123");
		
		Connection connection = connectionFactory.newConnection();
		
		return connection;
	}
	
}
