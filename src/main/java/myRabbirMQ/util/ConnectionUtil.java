package myRabbirMQ.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * ��ȡMQ������
 *
 */
public class ConnectionUtil {

	public static Connection getConnection() throws IOException, TimeoutException{
		
		//����һ�����ӹ���
		ConnectionFactory connectionFactory = new ConnectionFactory();
		
		//���÷����ַ
		connectionFactory.setHost("127.0.0.1");
		
		//���ö˿� AMQP 5672
		connectionFactory.setPort(5672);
		
		//����vhost
		connectionFactory.setVirtualHost("/vhost1");
		
		//�û���
		connectionFactory.setUsername("admin");
		//����
		connectionFactory.setPassword("123");
		
		Connection connection = connectionFactory.newConnection();
		
		return connection;
	}
	
}
