package ru.nsu.internship.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.Subscription;
import ru.nsu.internship.data.TemplateParameters;
import ru.nsu.internship.exceptions.NoRecipientsException;
import ru.nsu.internship.exceptions.NoSuchTemplateException;
import ru.nsu.internship.service.TemplateService;

import java.io.IOException;

@RestController
@RequestMapping("/template")
public class TemplateController {
    private final TemplateService service;

    public TemplateController(@Qualifier("templateService") TemplateService service) {
        this.service = service;
    }

    @PostMapping("/load")
    public ResponseEntity<String> loadTemplate(@RequestBody TemplateParameters parameters){
        if (parameters == null || parameters.getTemplate() == null || parameters.getTemplateId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong format of template");
        try {
            service.saveTemplate(parameters);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Illegal argument");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Template created");
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Report report) {
        if (report == null || report.getTemplateId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No template id");
        Message message;
        try {
            message = service.sendMessage(report);
        } catch (RuntimeException | IOException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sending message failed or wrong type provided");
        } catch (NoRecipientsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Template has no recipients");
        }
        return ResponseEntity.status(HttpStatus.OK).body(message.getMessage());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<Subscription> subscribeOnMessage(@RequestParam("time") Long time,
                                                           @RequestBody Report report) throws NoRecipientsException {
        if (report == null || report.getTemplateId() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        Subscription subscription = service.subscribeOnMessage(report, time);
        if (subscription == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        return ResponseEntity.status(HttpStatus.OK).body(subscription);
    }

    @PostMapping("/unsubscribe")
    public ResponseEntity<String> unsubscribeMessage(@RequestBody Subscription subscription) {
        if (subscription == null || subscription.getMessage() == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong format of subscription");
        try {
            service.unsubscribeOnMessage(subscription);
        } catch (NoRecipientsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("There is no such subscription");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Subscription deleted");
    }

    @GetMapping("/get")
    public ResponseEntity<TemplateParameters> getTemplate(@RequestParam("templateId") String templateId) {
        if (templateId == null || templateId.length() == 0)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        TemplateParameters parameters;
        try {
            parameters = service.getTemplate(templateId);
        } catch (NullPointerException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(parameters);
    }

    @PatchMapping("/{id}/addRecipient")
    public ResponseEntity<String> addRecipient(@PathVariable("id") String templateId, @RequestParam("url") String url){
        if (service.addRecipient(templateId, url) == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The recipient's already subscribed");
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PatchMapping("/{id}/deleteRecipient")
    public ResponseEntity<String> deleteRecipient(@PathVariable("id") String templateId, @RequestParam("url") String url){
        if (service.deleteRecipient(templateId, url) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such recipient");
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
