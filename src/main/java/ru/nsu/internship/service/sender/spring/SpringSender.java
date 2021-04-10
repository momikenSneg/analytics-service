package ru.nsu.internship.service.sender.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.service.sender.MessageSender;
import ru.nsu.internship.service.sender.httpclient.StandardSender;

@Component("springSender")
public class SpringSender implements MessageSender {
    private static final Logger log = LoggerFactory.getLogger(StandardSender.class);
    @Override
    public void send(Message message, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Message> requestBody = new HttpEntity<>(message);

        ResponseEntity<Message> result
                = restTemplate.postForEntity(url, requestBody, Message.class);

        // TODO log
        if (result.getStatusCode() == HttpStatus.OK) {
            log.info("Message " + message.getMessage() + " sent for " + url);
        } else {
            log.warn("Bad request when message " + message.getMessage() + " send for " + url);
        }

    }
}
