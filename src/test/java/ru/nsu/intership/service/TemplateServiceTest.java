package ru.nsu.intership.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestClientException;
import ru.nsu.internship.Main;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.Subscription;
import ru.nsu.internship.data.TemplateParameters;
import ru.nsu.internship.exceptions.NoRecipientsException;
import ru.nsu.internship.models.Recipient;
import ru.nsu.internship.models.Template;
import ru.nsu.internship.models.VarType;
import ru.nsu.internship.repository.RecipientRepository;
import ru.nsu.internship.repository.TemplateRepository;
import ru.nsu.internship.repository.VarTypeRepository;
import ru.nsu.internship.service.TemplateService;
import ru.nsu.internship.service.sender.MessageSender;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {Main.class})
public class TemplateServiceTest {
    @MockBean
    private TemplateRepository templateRepository;
    @MockBean
    private RecipientRepository recipientRepository;
    @MockBean
    private VarTypeRepository varTypeRepository;
    @MockBean
    @Qualifier("springSender")
    private MessageSender sender;
    private static final String qualifier = "templateService";

    private TemplateParameters parameters;
    private Template template;
    private Report report;

    @BeforeEach
    void init() {
        parameters = new TemplateParameters();
        parameters.setTemplateId("Report");
        parameters.setTemplate("The overall score was $mark$, the date: $date$");
        List<String> urls = new ArrayList<>();
        urls.add("https://httpbin.org/post");
        urls.add("https://postman-echo.com/post");
        parameters.setRecipients(urls);
        Map<String, String> types = new HashMap<>();
        types.put("mark", "double");
        types.put("date", "date");
        parameters.setVarTypes(types);

        template = new Template(parameters.getTemplateId(), parameters.getTemplate());
        template.addRecipient(new Recipient("https://httpbin.org/post"));
        template.addRecipient(new Recipient("https://httpbin.org/post"));
        template.addVarType(new VarType("mark", "double"));
        template.addVarType(new VarType("date", "date"));

        report = new Report();
        report.setTemplateId("Report");
        Map<String, String> vars = new HashMap<>();
        vars.put("mark", "5.3");
        vars.put("date", "03/04/2021");
        report.setVariables(vars);
    }

    @Test
    public void teasAddTemplate(@Autowired @Qualifier(qualifier) TemplateService service){
        when(templateRepository.findById(parameters.getTemplateId())).thenReturn(Optional.empty());
        doAnswer((i) -> {
            assertEquals(template, i.getArgument(0));
            return null;
        }).when(varTypeRepository).deleteAllByTemplate(template);
        doAnswer((i) -> {
            assertEquals(template, i.getArgument(0));
            return null;
        }).when(recipientRepository).deleteAllByTemplate(template);
        when(templateRepository.save(template)).thenReturn(template);

        Template returned = service.saveTemplate(parameters);
        returned.getRecipients().forEach(e -> e.setTemplate(null));
        returned.getVarTypes().forEach(e -> e.setTemplate(null));

        assertEquals(template.getTemplate(), returned.getTemplate());
        assertEquals(template.getTemplateId(), returned.getTemplateId());
    }

    @Test
    public void testUpdateTemplate(@Autowired @Qualifier(qualifier) TemplateService service){
        Template template = new Template(parameters.getTemplateId(), parameters.getTemplate());
        template.addRecipient(new Recipient("https://httpbin.org/post"));
        template.addRecipient(new Recipient("https://postman-echo.com/post"));
        template.addVarType(new VarType("mark", "double"));
        template.addVarType(new VarType("date", "date"));

        when(templateRepository.findById(parameters.getTemplateId())).thenReturn(Optional.of(template));

       // when(templateRepository.findById(parameters.getTemplateId())).thenReturn(null);
        //when(templateRepository.findById(parameters.getTemplateId()).get()).thenReturn(template);
        doAnswer((i) -> {
            assertEquals(template, i.getArgument(0));
            return null;
        }).when(varTypeRepository).deleteAllByTemplate(template);
        doAnswer((i) -> {
            assertEquals(template, i.getArgument(0));
            return null;
        }).when(recipientRepository).deleteAllByTemplate(template);
        when(templateRepository.save(template)).thenReturn(template);

        assertEquals(template, service.saveTemplate(parameters));
    }

    @Test
    public void testGetTemplate(@Autowired @Qualifier(qualifier) TemplateService service){
        String templateId = "Report";
        when(templateRepository.findById(templateId)).thenReturn(Optional.of(template));
        TemplateParameters returned = service.getTemplate(templateId);

        assertEquals(parameters, returned);
    }

    @Test
    public void testGetNullTemplate(@Autowired @Qualifier(qualifier) TemplateService service){
        String templateId = "Report";
        when(templateRepository.findById(templateId)).thenReturn(Optional.empty());
        assertThrows(NullPointerException.class, () -> service.getTemplate(templateId));
    }

    @Test
    public void testAddRecipient(@Autowired @Qualifier(qualifier) TemplateService service){
        String templateId = "Report";
        String url = "https://httpbin.org/post";
        when(recipientRepository.getFirstByTemplateIdAndUrl(templateId, url)).thenReturn(null);
        doAnswer((i) -> {
            assertEquals(templateId, i.getArgument(0));
            assertEquals(url, i.getArgument(1));
            return null;
        }).when(recipientRepository).deleteByTemplateIdAndUrl(templateId, url);
        assertEquals(service.addRecipient(templateId, url), url);
    }

    @Test
    public void testAddExistingRecipient(@Autowired @Qualifier(qualifier) TemplateService service){
        String templateId = "Report";
        String url = "https://httpbin.org/post";
        when(recipientRepository.getFirstByTemplateIdAndUrl(templateId, url)).thenReturn(new Recipient(url));
        assertNull(service.addRecipient(templateId, url));
    }

    @Test
    public void testDeleteRecipient(@Autowired @Qualifier(qualifier) TemplateService service){
        String templateId = "Report";
        String url = "https://httpbin.org/post";
        when(recipientRepository.getFirstByTemplateIdAndUrl(templateId, url)).thenReturn(new Recipient(url));
        doAnswer((i) -> {
            assertEquals(templateId, i.getArgument(0));
            assertEquals(url, i.getArgument(1));
            return null;
        }).when(recipientRepository).deleteByTemplateIdAndUrl(templateId, url);
        assertEquals(service.deleteRecipient(templateId, url), url);
    }

    @Test
    public void testDeleteNoSuchRecipient(@Autowired @Qualifier(qualifier) TemplateService service){
        String templateId = "Report";
        String url = "https://httpbin.org/post";
        when(recipientRepository.getFirstByTemplateIdAndUrl(templateId, url)).thenReturn(null);
        assertNull(service.deleteRecipient(templateId, url));
    }

    @Test
    public void testSendMessage(@Autowired @Qualifier(qualifier) TemplateService service) throws NoRecipientsException, IOException {
        String message = "The overall score was 5.3, the date: 03/04/2021";

        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        doAnswer((i) -> {
            assertEquals("https://httpbin.org/post", i.getArgument(1));
            return null;
        }).when(sender).send(new Message(message), "https://httpbin.org/post");
        doAnswer((i) -> {
            assertEquals("https://postman-echo.com/post", i.getArgument(1));
            return null;
        }).when(sender).send(new Message(message), "https://postman-echo.com/post");

        assertEquals(message, service.sendMessage(report).getMessage());
    }

    @Test
    public void testSendWithNoRecipients(@Autowired @Qualifier(qualifier) TemplateService service){
        template.setRecipients(null);
        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        assertThrows(NoRecipientsException.class, () -> service.sendMessage(report));
        template.setRecipients(new ArrayList<>());
        assertThrows(NoRecipientsException.class, () -> service.sendMessage(report));
    }

    @Test
    public void testSendOnWrongUrl(@Autowired @Qualifier(qualifier) TemplateService service) throws IOException {
        List<Recipient> rec = new ArrayList<>();
        rec.add(new Recipient("hi"));
        template.setRecipients(rec);

        String message = "The overall score was 5.3, the date: 03/04/2021";
        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        doThrow(new RestClientException("")).when(sender).send(new Message(message),"hi");

        assertThrows(RuntimeException.class, () -> service.sendMessage(report));
    }

    @Test
    public void testSubscribeOnMessage(@Autowired @Qualifier(qualifier) TemplateService service) throws NoRecipientsException {
        long period = 5000;
        Subscription subscription = new Subscription("The overall score was 5.3, the date: 03/04/2021",
                parameters.getRecipients());

        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        assertEquals(subscription, service.subscribeOnMessage(report, period));

    }

    @Test
    public void testSubscribeOnNoRecipients(@Autowired @Qualifier(qualifier) TemplateService service){
        long period = 5000;
        template.setRecipients(new ArrayList<>());
        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        assertThrows(NoRecipientsException.class, () -> service.subscribeOnMessage(report, period));
        template.setRecipients(null);
        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        assertThrows(NoRecipientsException.class, () -> service.subscribeOnMessage(report, period));
    }

    @Test
    public void testMakeExistingSubscription(@Autowired @Qualifier(qualifier) TemplateService service) throws NoRecipientsException {
        long period = 5000;
        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        service.subscribeOnMessage(report, period);

        assertNull(service.subscribeOnMessage(report, period));
    }

    @Test
    public void testUnsubscribeFromMessage(@Autowired @Qualifier(qualifier) TemplateService service) throws NoRecipientsException {
        long period = 5000;
        Subscription subscription = new Subscription("The overall score was 5.3, the date: 03/04/2021",
                parameters.getRecipients());
        when(templateRepository.findById("Report")).thenReturn(Optional.of(template));
        service.subscribeOnMessage(report, period);
        service.unsubscribeOnMessage(subscription);
    }

    @Test
    public void testUnsubscribeFromNoExistingSubscription(@Autowired @Qualifier(qualifier) TemplateService service) {
        long period = 5000;
        Subscription subscription = new Subscription("The overall score was 5.3, the date: 03/04/2021",
                parameters.getRecipients());
        try {
            service.unsubscribeOnMessage(subscription);
        } catch (NoRecipientsException ignored){ }
        assertThrows(NoRecipientsException.class, () -> service.unsubscribeOnMessage(subscription));assertThrows(NoRecipientsException.class, () -> service.unsubscribeOnMessage(subscription));
    }
}
