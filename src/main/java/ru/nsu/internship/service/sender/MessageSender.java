package ru.nsu.internship.service.sender;

import ru.nsu.internship.data.Message;

import java.io.IOException;

public interface MessageSender {
    void send(Message message, String url) throws IOException;
}
