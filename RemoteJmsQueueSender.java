package com.jmsexample;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class RemoteJmsQueueSender {

    public static void main(String args[]) throws NamingException, JMSException {
        // add JMS implementation jar in classpath and
        // replace INITIAL_CONTEXT_FACTORY, PROVIDER_URL, FACTORY_NAME, QUEUE_NAME, USERNAME and PASSWORD.
        Hashtable properties = new Hashtable(2);
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "INITIAL_CONTEXT_FACTORY");
        properties.put(Context.PROVIDER_URL, "PROVIDER_URL");

        InitialContext jndiContext = new InitialContext(properties);
        // perform JNDI lookup from remote machine
        ConnectionFactory factory = (QueueConnectionFactory) jndiContext.lookup("FACTORY_NAME");
        Queue queue = (Queue) jndiContext.lookup("QUEUE_NAME");
        Connection connection = factory.createConnection("USERNAME", "PASSWORD");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        MessageProducer producer = session.createProducer(queue);
        TextMessage message = session.createTextMessage();
        message.setText("This is message ");
        System.out.println("Sending message: " + message.getText());
        producer.send(message);
    }

}
