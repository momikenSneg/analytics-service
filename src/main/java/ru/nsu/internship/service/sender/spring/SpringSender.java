package ru.nsu.internship.service.sender.spring;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.service.sender.MessageSender;

@Component("springSender")
public class SpringSender implements MessageSender {
    //@Autowired
    private final Logger log;

    public SpringSender(Logger log) {
        this.log = log;
    }

    @Override
    public void send(Message message, String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(message.getMessage());
        try {
            ResponseEntity<String> result = restTemplate.postForEntity(url, requestBody, String.class);
            if (result.getStatusCode() == HttpStatus.OK) {
                log.info("Message " + message.getMessage() + " sent for " + url);
            } else {
                log.warn("Bad request when message " + message.getMessage() + " send for " + url);
            }
        } catch (HttpClientErrorException e){
            log.error("Bad request when message " + message.getMessage() + " send for " + url);
        }
    }
}
