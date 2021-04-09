package ru.nsu.internship.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nsu.internship.Utils;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.data.TemplateParameters;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.models.Recipient;
import ru.nsu.internship.models.Template;
import ru.nsu.internship.models.VarType;
import ru.nsu.internship.repository.RecipientRepository;
import ru.nsu.internship.repository.TemplateRepository;
import ru.nsu.internship.repository.VarTypeRepository;
import ru.nsu.internship.service.sender.MessageSender;

import java.io.IOException;

@Service
@Qualifier("templateService")
public class MyTemplateService implements TemplateService {

    private final TemplateRepository templateRepository;
    private final RecipientRepository recipientRepository;
    private final VarTypeRepository varTypeRepository;
    private final MessageSender sender;

    @Autowired
    public MyTemplateService(TemplateRepository templateRepository,
                             RecipientRepository recipientRepository,
                             VarTypeRepository varTypeRepository,
                             @Qualifier("standardSender") MessageSender sender) {
        this.templateRepository = templateRepository;
        this.recipientRepository = recipientRepository;
        this.varTypeRepository = varTypeRepository;
        this.sender = sender;
    }

    @Override
    @Transactional
    public void saveTemplate(TemplateParameters parameters) {
//        if (templateRepository.findById(parameters.getTemplateId()).isPresent()){
//            Template template = templateRepository.findById(parameters.getTemplateId()).get();
//            template.setTemplate(parameters.getTemplate());
//            parameters.getRecipients().forEach(e -> {
//                if (!recipientRepository.existsByTemplateAndUrl(template, e)){
//                    Recipient recipient = new Recipient(e, template);
//                    template.addRecipient(recipient);
//                }
//                varTypeRepository.deleteAllByTemplate(template);
//            });
//        }
//        Template template = new Template();
//        template.setTemplateId(parameters.getTemplateId());
//        template.setTemplate(parameters.getTemplate());

        Template template;
        if (templateRepository.findById(parameters.getTemplateId()).isPresent()) {
            template = templateRepository.findById(parameters.getTemplateId()).get();
            template.setTemplate(parameters.getTemplate());
            varTypeRepository.deleteAllByTemplate(template);
            recipientRepository.deleteAllByTemplate(template);
        } else {
            template = new Template(parameters.getTemplateId(), parameters.getTemplate());
        }
        if (parameters.getRecipients() != null)
            parameters.getRecipients().forEach(e -> {
                Recipient recipient = new Recipient(e, template);
                template.addRecipient(recipient);
            });
        if (parameters.getVarTypes() != null) {
            parameters.getVarTypes().forEach((k, v) -> {
                VarType type = new VarType(k, v, template);
                template.addVarType(type);
            });
        }
        templateRepository.save(template);
    }

    @Override
    public void addRecipient(String templateId, String recipient) {

    }

    @Override
    public void deleteRecipient(String templateId, String recipient) {

    }

    //TODO обработать нпе и ошибку из форича
    //TODO сообщать если некому отправить сообщение
    @Override
    public void sendMessage(Report report) {
        Template template = templateRepository.findById(report.getTemplateId()).orElseThrow(NullPointerException::new);
        Message message = Utils.makeMessage(template.getTemplate(), report.getVariables());
        template.getRecipients().forEach(e -> {
            try {
                sender.send(message, e.getUrl());
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        });
    }

}
