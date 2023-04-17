package app.alertClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.jms.JMSException;

@ShellComponent
public class AlertClientCommands {

    @Autowired
    private JmsTemplate jmsTemplate;

    private Subscriber sub = new Subscriber();

    @ShellMethod("Subscribe to topic")
    public String subscribe() throws JMSException{
        sub.create("22", "Alert.UT");

        return "Subscribed to topic";
    }

    @ShellMethod("Subscribe arrival alert")
    public String alert() throws JMSException{
        String greeting = sub.getGreeting(5000);

        return greeting;
    }
}
