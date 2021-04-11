package ru.nsu.intership.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.nsu.internship.Main;
import ru.nsu.internship.controller.TemplateController;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.Subscription;
import ru.nsu.internship.data.TemplateParameters;
import ru.nsu.internship.exceptions.NoRecipientsException;
import ru.nsu.internship.service.MyTemplateService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {Main.class})
@AutoConfigureMockMvc
public class TemplateControllerTest {
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MyTemplateService service;
    @Autowired
    private MockMvc mockMvc;

//    @AfterEach
//    public void resetDb() {
//        repository.deleteAll();
//    }

    @Test
    void testRightParametersSave() throws Exception {
        TemplateParameters parameters = new TemplateParameters();
        parameters.setTemplateId("MeetingId");
        parameters.setTemplate("The meeting will take place in the $place$ at $time$ o'clock");
        mockMvc.perform(
                post("/template/load")
                        .content(objectMapper.writeValueAsString(parameters))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.name").value("Michail"));
    }

    @Test
    void testWrongParametersSave() throws Exception {
        TemplateParameters parameters = new TemplateParameters();
        parameters.setTemplateId(null);
        parameters.setTemplate("The meeting will take place in the $place$ at $time$ o'clock");
        mockMvc.perform(
                post("/template/load")
                        .content(objectMapper.writeValueAsString(parameters))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testIllegalArgumentsSave() throws Exception {
        TemplateParameters parameters = new TemplateParameters();
        parameters.setTemplateId(null);
        parameters.setTemplate("The meeting will take place in the $place$ at $time$ o'clock");
        doThrow(new IllegalArgumentException("")).when(service).saveTemplate(parameters);
        mockMvc.perform(
                post("/template/load")
                        .content(objectMapper.writeValueAsString(parameters))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSend() throws Exception {
        Report report = new Report();
        report.setTemplateId("Report");
        Map<String, String> vars = new HashMap<>();
        vars.put("mark", "5.3");
        ;
        report.setVariables(vars);
        when(service.sendMessage(report)).thenReturn(new Message("H"));
        mockMvc.perform(
                post("/template/send")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testNoRecipientsSend() throws Exception {
        Report report = new Report();
        report.setTemplateId("Report");
        Map<String, String> vars = new HashMap<>();
        vars.put("mark", "5.3");
        ;
        report.setVariables(vars);
        doThrow(new NoRecipientsException("")).when(service).sendMessage(report);
        mockMvc.perform(
                post("/template/send")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSubscribeOnMessage() throws Exception {
        Report report = new Report();
        report.setTemplateId("Report");
        Map<String, String> vars = new HashMap<>();
        vars.put("mark", "5.3");
        report.setVariables(vars);
        when(service.subscribeOnMessage(report, 5000)).thenReturn(new Subscription());
        mockMvc.perform(
                post("/template/subscribe")
                        .param("time", "5000")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSubscribeWrongArgsOnMessage() throws Exception {
        Report report = new Report();
        report.setTemplateId(null);
        Map<String, String> vars = new HashMap<>();
        vars.put("mark", "5.3");
        report.setVariables(vars);
        //when(service.subscribeOnMessage(report, 5000)).thenReturn(new Subscription());
        mockMvc.perform(
                post("/template/subscribe")
                        .param("time", "5000")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSubscribeOnNoExistingMessage() throws Exception {
        Report report = new Report();
        report.setTemplateId("Report");
        Map<String, String> vars = new HashMap<>();
        vars.put("mark", "5.3");
        report.setVariables(vars);
        when(service.subscribeOnMessage(report, 5000)).thenReturn(null);
        mockMvc.perform(
                post("/template/subscribe")
                        .param("time", "5000")
                        .content(objectMapper.writeValueAsString(report))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUnsubscribe() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setMessage("Subs");
        mockMvc.perform(
                post("/template/unsubscribe")
                        .content(objectMapper.writeValueAsString(subscription))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testWrongArgsUnsubscribe() throws Exception {
        mockMvc.perform(
                post("/template/unsubscribe")
                        .content(objectMapper.writeValueAsString(new Subscription()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testNoUnsubscribe() throws Exception {
        Subscription subscription = new Subscription();
        subscription.setMessage("Subs");
        subscription.setUrls(new ArrayList<>());
        doThrow(new NoRecipientsException("")).when(service).unsubscribeOnMessage(subscription);
        mockMvc.perform(
                post("/template/unsubscribe")
                        .content(objectMapper.writeValueAsString(subscription))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetTemplate() throws Exception {
        TemplateParameters parameters = new TemplateParameters();
        parameters.setTemplateId("Report");
        parameters.setTemplate("The overall score was $mark$");
        List<String> urls = new ArrayList<>();
        urls.add("https://httpbin.org/post");
        parameters.setRecipients(urls);
        Map<String, String> types = new HashMap<>();
        types.put("mark", "double");
        parameters.setVarTypes(types);

        when(service.getTemplate(parameters.getTemplateId())).thenReturn(parameters);
        mockMvc.perform(
                get("/template/get")
                        .param("templateId", "Report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("templateId").value("Report"));
    }

    @Test
    void testGetWrongArgsTemplate() throws Exception {

       // when(service.getTemplate(parameters.getTemplateId())).thenReturn(parameters);
        mockMvc.perform(
                get("/template/get")
                        .param("templateId", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetNoExistingTemplate() throws Exception {
        doThrow(new NullPointerException("")).when(service).getTemplate("Id");
        mockMvc.perform(
                get("/template/get")
                        .param("templateId", "Id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tesAddRecipient() throws Exception {
        when(service.addRecipient("Report", "https://httpbin.org/post")).thenReturn("https://httpbin.org/post");
        mockMvc.perform(
                patch("/template/Report/addRecipient")
                        .param("url", "https://httpbin.org/post"))
                .andExpect(status().isOk());
    }

    @Test
    void tesAddExistingRecipient() throws Exception {
        when(service.addRecipient("Report", "https://httpbin.org/post")).thenReturn(null);
        mockMvc.perform(
                patch("/template/Report/addRecipient")
                        .param("templateId", "Id"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void tesDeleteRecipient() throws Exception {
        when(service.deleteRecipient("Report", "https://httpbin.org/post")).thenReturn("https://httpbin.org/post");
        mockMvc.perform(
                patch("/template/Report/deleteRecipient")
                        .param("url", "https://httpbin.org/post"))
                .andExpect(status().isOk());
    }

    @Test
    void tesDeleteNoExistingRecipient() throws Exception {
        when(service.deleteRecipient("Report", "https://httpbin.org/post")).thenReturn(null);
        mockMvc.perform(
                patch("/template/Report/deleteRecipient")
                        .param("url", "https://httpbin.org/post"))
                .andExpect(status().isBadRequest());
    }

}
