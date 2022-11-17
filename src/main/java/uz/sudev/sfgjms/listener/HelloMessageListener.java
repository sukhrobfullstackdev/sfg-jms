package uz.sudev.sfgjms.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import uz.sudev.sfgjms.configuration.JMSConfiguration;
import uz.sudev.sfgjms.model.HelloWorldMessage;

import javax.jms.Message;

@Component
public class HelloMessageListener {
    @JmsListener(destination = JMSConfiguration.MY_QUEUE)
    public void listen(@Payload HelloWorldMessage helloWorldMessage,
                        @Headers MessageHeaders messageHeaders,
                        Message message) {
        System.out.println("message: " + message);
        System.out.println("messageHeaders: " + messageHeaders);
        System.out.println("helloWorldMessage: " + helloWorldMessage.getMessage());
        System.out.println("Got a message!");
    }
}
