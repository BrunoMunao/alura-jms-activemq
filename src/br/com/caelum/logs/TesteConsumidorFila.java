package br.com.caelum.logs;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

public class TesteConsumidorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		// Session session = connection.createSession(true, Session.SESSION_TRANSACTED);
		
		Destination fila = (Destination) context.lookup("logs");
		MessageConsumer consumer = session.createConsumer(fila);
		// MessageConsumer consumer = session.createConsumer(fila, "JMSPriority > 6" ); // recebendo mensagens da fila com prioridade maior que 6
		
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {

				TextMessage textMessage = (TextMessage)message;
				
				try {					
					System.out.println(textMessage.getText());
					// message.acknowledge();
					// session.commit();
				} catch (JMSException e) {
					e.printStackTrace();					
					// session.rollback();
				}
			}
			
		});
		
				
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
