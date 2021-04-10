package ru.nsu.internship.service;

import org.springframework.stereotype.Service;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.Subscription;
import ru.nsu.internship.data.TemplateParameters;
import ru.nsu.internship.exceptions.NoRecipientsException;
import ru.nsu.internship.models.Template;

import java.io.IOException;

@Service
public interface TemplateService {
    Template saveTemplate(TemplateParameters templateParameters);
    TemplateParameters getTemplate(String templateId);
    String addRecipient(String templateId, String recipient);
    String deleteRecipient(String templateId, String recipient);
    Message sendMessage(Report report) throws IOException, NoRecipientsException;
    Subscription subscribeOnMessage(Report report, long period) throws NoRecipientsException;
    void unsubscribeOnMessage(Subscription report) throws NoRecipientsException;
}
