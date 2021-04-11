package ru.nsu.intership.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import ru.nsu.internship.data.Message;
import ru.nsu.internship.service.MessageMaker;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MessageMakerTest extends Assert {

    @Test
    public void testCheckRightTypes() {
        String template = "The meeting will take place in the $place$ at $time$. " +
                "The overall score was $score$, the date: $date$. And your mark is $mark$";
        Map<String, String> variables = new HashMap<>();
        variables.put("place", "Room No.5");
        variables.put("time", "11:00");
        variables.put("score", "11.5");
        variables.put("date", "17/05/2021");
        variables.put("mark", "5");
        Map<String, String> types = new HashMap<>();
        types.put("place", "string");
        types.put("time", "time");
        types.put("score", "double");
        types.put("date", "date");
        types.put("mark", "int");

        Message message = MessageMaker.make(template, variables, types);
        assertEquals("The meeting will take place in the Room No.5 at 11:00. " +
                "The overall score was 11.5, the date: 17/05/2021. And your mark is 5", message.getMessage());
    }

    @Test
    public void testCheckWrongType() {
        String template = "The overall score was $score$";
        Map<String, String> variables = new HashMap<>();
        variables.put("score", "eleven");
        Map<String, String> types = new HashMap<>();
        types.put("score", "double");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> MessageMaker.make(template, variables, types));
        assertEquals("Type does not match value", exception.getMessage());
    }

    @Test
    public void testCheckNoExistingType() {
        String template = "The overall score was $score$";
        Map<String, String> variables = new HashMap<>();
        variables.put("score", "eleven");
        Map<String, String> types = new HashMap<>();
        types.put("score", "vera");
        Exception exception = assertThrows(IllegalArgumentException.class, () -> MessageMaker.make(template, variables, types));
        assertEquals("No such type", exception.getMessage());
    }

    @Test
    public void testWithoutTypes() {
        String template = "The meeting will take place in the $place$ at $time$.";
        Map<String, String> variables = new HashMap<>();
        variables.put("place", "Room No.5");
        variables.put("time", "11:00");
        Message message = MessageMaker.make(template, variables, null);
        assertEquals("The meeting will take place in the Room No.5 at 11:00.", message.getMessage());
    }
    
    @Test
    public void TestEscapes(){
        String template = "The meeting will take place in the $$$ at $.^$.";
        Map<String, String> variables = new HashMap<>();
        variables.put("$", "Room No.5");
        variables.put(".^", "11:00");
        Map<String, String> types = new HashMap<>();
        types.put("$", "string");
        types.put(".^", "time");

        Message message = MessageMaker.make(template, variables, types);
        assertEquals("The meeting will take place in the Room No.5 at 11:00.", message.getMessage());
    }

}
