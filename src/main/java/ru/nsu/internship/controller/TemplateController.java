package ru.nsu.internship.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.TemplateParameters;
import ru.nsu.internship.models.Template;
import ru.nsu.internship.service.TemplateService;

import java.io.IOException;

@RestController
@RequestMapping("/template")
public class TemplateController {
    private final TemplateService service;

    public TemplateController(@Qualifier("templateService") TemplateService service) {
        this.service = service;
    }


    //TODO Можно ли не подать типы совсем и будет ли там тогда просто нал
    //TODO Для тестирования подать налл подать пустой список и тд
    @PostMapping("/load")
    public Template loadTemplate(@RequestBody TemplateParameters parameters){
        if (parameters == null || parameters.getTemplate() == null || parameters.getTemplateId() == null)
            //TODO подумать над исключениями
            throw new IllegalArgumentException();
        service.saveTemplate(parameters);
        return null;
    }

    //TODO обоаботать ошибки
    @PostMapping("/send")
    public Template sendMessage(@RequestBody Report report) throws IOException {
        if (report == null || report.getTemplateId() == null)
            //TODO подумать над исключениями
            throw new IllegalArgumentException();
        System.out.println(report);
        service.sendMessage(report);
        return null;
    }

    @GetMapping("/get")
    public TemplateParameters getTemplate(@RequestParam("templateId") String templateId) {
        if (templateId == null)
            throw new IllegalArgumentException();

        return service.getTemplate(templateId);
    }

    @PatchMapping("/{id}/addRecipient")
    public String addRecipient(@PathVariable("id") String templateId, @RequestParam("url") String url){
        return service.addRecipient(templateId, url);
    }

    @PatchMapping("/{id}/deleteRecipient")
    public String deleteRecipient(@PathVariable("id") String templateId, @RequestParam("url") String url){
        return service.deleteRecipient(templateId, url);
    }
}
