package app.alertClient;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class Subscriber {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(Subscriber.class);


    private String clientId;
    private Connection connection;
    private MessageConsumer messageConsumer;

    public String getClientId() {
        return clientId;
    }

    public void create(String clientId, String topicName)
            throws JMSException {
        this.clientId = clientId;

        // create a Connection Factory
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory(
                        ActiveMQConnection.DEFAULT_BROKER_URL);

        // create a Connection
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);

        // create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // create the Topic from which messages will be received
        Topic topic = session.createTopic(topicName);

        // create a MessageConsumer for receiving messages
        messageConsumer = session.createConsumer(topic);

        // start the connection in order to receive messages
        connection.start();
    }

    public void closeConnection() throws JMSException {
        connection.close();
    }

    public String getAlerts() throws JMSException {

        String text = "No message";
        // read a message from the topic destination

        Message message = messageConsumer.receive(5000);

        // check if a message was received
        if (message != null) {
            // cast the message to the correct type
            TextMessage textMessage = (TextMessage) message;

            // retrieve the message content
            text = textMessage.getText();
            LOGGER.debug(clientId + ": received message with text='{}'",
                    text);

        } else {
            LOGGER.debug(clientId + ": no message received");
        }

        return text;
    }
}