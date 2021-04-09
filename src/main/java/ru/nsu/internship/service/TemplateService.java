package ru.nsu.internship.service;

import org.springframework.stereotype.Service;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.TemplateParameters;

import java.io.IOException;

@Service
public interface TemplateService {
    void saveTemplate(TemplateParameters templateParameters);
    void addRecipient(String templateId, String recipient);
    void deleteRecipient(String templateId, String recipient);
    void sendMessage(Report report) throws IOException;
}
