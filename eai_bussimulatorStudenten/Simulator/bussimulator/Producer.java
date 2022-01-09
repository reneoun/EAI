package bussimulator;

import java.util.Date;

import javax.jms.*;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
//  TODO hier de naam van de destination invullen
    private static String subject = "MessageQ";
    
    private Session session;
    private Connection connection;
    private MessageProducer producer;
    
    public Producer() {
        
    }
    
    public void sendBericht(String bericht) {
    	try {
    		createConnection();
    		sendTextMessage(bericht);
            connection.close();
    	} catch (JMSException e) {
    		e.printStackTrace();
    	}
    }
        
    
    private void createConnection() throws JMSException {
       ConnectionFactory connectionFactory =
           new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
//		TODO maak de connection aan
       connection = connectionFactory.createConnection();
       connection.start();
//		TODO maak de session aan
       session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//		TODO maak de destination aan (gebruik de subject variabele als naam)
      Destination destination = session.createQueue(subject);
//		TODO maak de producer aan
      producer = session.createProducer(destination);
      producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
       }
    
    
    private void sendTextMessage(String themessage) throws JMSException {
//		TODO maak de message aan
      TextMessage msg = session.createTextMessage(themessage);
      producer.send(msg);
    }    
}
