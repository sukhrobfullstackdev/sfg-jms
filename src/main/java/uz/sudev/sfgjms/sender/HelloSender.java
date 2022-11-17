package uz.sudev.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.sudev.sfgjms.configuration.JMSConfiguration;
import uz.sudev.sfgjms.model.HelloWorldMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;
@RequiredArgsConstructor
@Component
public class HelloSender {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello Motherfucker!")
                .build();

        jmsTemplate.convertAndSend(JMSConfiguration.MY_QUEUE, message);
    }
    // This 'sendAndReceiveMessage' method for sending message and receive message which send in HelloMessageListener!
    @Scheduled(fixedRate = 2000)
    public void sendAndReceiveMessage(){
        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello Motherfucker Ibo!")
                .build();
        // Using JmsTemplate we are sending message to MY_SEND_AND_RECEIVE_QUEUE destination. And we receive response in receivedMessage object!
        Message receivedMessage = jmsTemplate.sendAndReceive(JMSConfiguration.MY_SEND_AND_RECEIVE_QUEUE, session -> {
            Message helloMessage;
            try {
                helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                helloMessage.setStringProperty("_type","uz.sudev.sfgjms.model.HelloWorldMessage");
            } catch (JsonProcessingException e) {
                throw new JMSException("JMS exception has been occurred!");
            }

            return helloMessage;
        });
        // Here we are receiving message which re-send message through jmsTemplate.convertAndSend() method!
        try {
            if (receivedMessage != null) {
                System.out.println(receivedMessage.getBody(String.class));
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
