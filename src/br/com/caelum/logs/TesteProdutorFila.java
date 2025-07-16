package br.com.caelum.logs;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;

public class TesteProdutorFila {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		
		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection = factory.createConnection(); 
		connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Destination fila = (Destination) context.lookup("logs");
		
		MessageProducer producer = session.createProducer(fila);

        Message message = session.createTextMessage("LOG");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 3, 50000); // Salva ou não - prioridade do menor ao maior (0 a 9) - tempo de vida (em milisegundos)

		message = session.createTextMessage("ERROR");
		producer.send(message, DeliveryMode.NON_PERSISTENT, 9, 50000); // Salva ou não - prioridade do menor ao maior (0 a 9) - tempo de vida (em milisegundos)		
				
		//new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}
}
