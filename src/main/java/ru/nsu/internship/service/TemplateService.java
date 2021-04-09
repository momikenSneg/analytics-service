package ru.nsu.internship.service;

import org.springframework.stereotype.Service;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.TemplateParameters;

import java.io.IOException;

@Service
public interface TemplateService {
    void saveTemplate(TemplateParameters templateParameters);
    TemplateParameters getTemplate(String templateId);
    String addRecipient(String templateId, String recipient);
    String deleteRecipient(String templateId, String recipient);
    void sendMessage(Report report) throws IOException;
}
