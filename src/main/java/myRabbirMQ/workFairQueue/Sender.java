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
		
		//��������
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		/**
		 * ÿ�������߷���ȷ����Ϣ֮ǰ�����в�������һ����Ϣ�������ߣ�
		 * һ��ֻ����һ����Ϣ
		 * ���Ʒ��͸�ͬһ����������Ϣ���ó���һ��
 		 */
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		
		
		
		
		for(int i=0;i<50;i++){
			String msg = " hello world "+i+ "..... ";
			channel.basicPublish("",QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(10);
		}
		System.out.println("��Ϣ���ͳɹ� .....");
		
		channel.close();
		connection.close();
	}
}