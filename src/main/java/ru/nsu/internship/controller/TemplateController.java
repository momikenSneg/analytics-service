package ru.nsu.internship.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import retrofit2.converter.gson.GsonConverterFactory;
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
}
