package cn.com.onlineVideoCoreApp.base;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MyActiveMqUtil {
	private static ConnectionFactory connectionFactory;
	private static Connection connection;

	public MyActiveMqUtil() throws JMSException {
		// 1.创建工厂
		connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, ActiveMQConnection.DEFAULT_BROKER_URL);
		mqConnection();
	}

	public MyActiveMqUtil(String userName, String password, String url) throws JMSException {
		// 1.创建工厂
		connectionFactory = new ActiveMQConnectionFactory(userName, password, url);
		mqConnection();
	}

	private void mqConnection() throws JMSException {
		// 2.创建连接
		connection = connectionFactory.createConnection();
		// 3.启动连接
		connection.start();
	}

	public void setMQProducer(String queueName, String text) throws JMSException {

		// 4.创建session
		Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
		// 5.创建队列
		Queue queue = session.createQueue(queueName);
		// 6.创建生产者
		MessageProducer producer = session.createProducer(queue);
		// 7.创建消息文字
		TextMessage textMessage = session.createTextMessage(text);
		// 8.发送消息
		producer.send(textMessage);
		// 9.提交消息
		session.commit();
		// 10.关闭session和connection
		if (null != session) {
			session.close();
		}

	}

	public String getMQConsumer(String queueName) throws JMSException {
		// 4.创建session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// 5.创建队列
		Queue queue = session.createQueue(queueName);
		// 6.创建生产者
		MessageConsumer producer = session.createConsumer(queue);

		TextMessage textMessage = (TextMessage) producer.receive(1000);

		// 10.关闭session和connection
		if (null != session) {
			session.close();
		}
		return textMessage.getText();
	}

}
