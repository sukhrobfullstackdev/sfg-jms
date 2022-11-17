package uz.sudev.sfgjms.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import uz.sudev.sfgjms.configuration.JMSConfiguration;
import uz.sudev.sfgjms.model.HelloWorldMessage;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class HelloMessageListener {
    private final JmsTemplate jmsTemplate;
    @JmsListener(destination = JMSConfiguration.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                        @Headers MessageHeaders messageHeaders,
                        Message message) {
        System.out.println("message: " + message);
        System.out.println("messageHeaders: " + messageHeaders);
        System.out.println("helloWorldMessage: " + helloWorldMessage.getMessage());
        System.out.println("Got a message!");
    }

    // We are receiving message which send in HelloSender class sendAndReceiveMessage() method through MY_SEND_AND_RECEIVE_QUEUE destination and re-sending through jmsTemplate.convertAndSend() method in 'try'
    @JmsListener(destination = JMSConfiguration.MY_SEND_AND_RECEIVE_QUEUE)
    public void listenForHello(@Payload HelloWorldMessage helloWorldMessage,
                        @Headers MessageHeaders messageHeaders,
                        Message message) {
        HelloWorldMessage helloWorld = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();
        try {
            jmsTemplate.convertAndSend(message.getJMSReplyTo(),helloWorld);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
        System.out.println("message listenForHello: " + message);
        System.out.println("messageHeaders listenForHello: " + messageHeaders);
        System.out.println("helloWorldMessage listenForHello: " + helloWorldMessage.getMessage());
        System.out.println("Got a message!");
    }

}
