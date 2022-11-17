package uz.sudev.sfgjms.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import uz.sudev.sfgjms.configuration.JMSConfiguration;
import uz.sudev.sfgjms.model.HelloWorldMessage;

import java.util.UUID;
@RequiredArgsConstructor
@Component
public class HelloSender {
    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 2000)
    public void sendMessage(){

        System.out.println("I'm Sending a message");

        HelloWorldMessage message = HelloWorldMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello Motherfucker!")
                .build();

        jmsTemplate.convertAndSend(JMSConfiguration.MY_QUEUE, message);

        System.out.println("Message Sent!");

    }
}
