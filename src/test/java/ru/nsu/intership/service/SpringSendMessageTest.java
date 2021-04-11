package ru.nsu.intership.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.nsu.internship.Main;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.service.sender.MessageSender;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {Main.class})
public class SpringSendMessageTest {
    @MockBean
    private Logger log;
    @Autowired
    @Qualifier("springSender")
    MessageSender sender;

    @Test
    public void testSendOnRightUrl() throws IOException {
        Message message = new Message("Hello, my name is Sara");
        String url = "https://httpbin.org/post";
        String logInfo = "Message Hello, my name is Sara sent for https://httpbin.org/post";
        doAnswer((i) -> {
            assertEquals(logInfo, i.getArgument(0));
            return null;
        }).when(log).info(logInfo);

        sender.send(message, url);
        verify(log, times(1)).info(logInfo);
        verify(log, times(0)).info("Bad request when message " + message.getMessage() + " send for " + url);

    }

    @Test
    public void testSendOnWrongUrl() throws IOException {
        Message message = new Message("Hello, my name is Sara");
        String url = "t";
        String logInfo = "Message Hello, my name is Sara sent for t";

        assertThrows(IllegalArgumentException.class, () -> sender.send(message, url));
        verify(log, times(0)).info(logInfo);
        verify(log, times(0)).warn("Bad request when message " + message.getMessage() + " send for " + url);

    }

    @Test
    public void testSendBadRequestUrl() throws IOException {
        Message message = new Message("Hello, my name is Sara");
        String url = "https://httpbin.org/get";
        String logInfo = "Message Hello, my name is Sara sent for t";
        doAnswer((i) -> {
            assertEquals(logInfo, i.getArgument(0));
            return null;
        }).when(log).info(logInfo);

        sender.send(message, url);
        verify(log, times(0)).info(logInfo);
        verify(log, times(1)).error("Bad request when message " + message.getMessage() + " send for " + url);

    }

}
