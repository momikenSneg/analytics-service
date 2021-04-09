package ru.nsu.internship;

import ru.nsu.internship.data.Message;

import java.util.Map;
import java.util.regex.Pattern;

public class Utils {
    public static Message makeMessage(String template, Map<String, String> variables){

        for (Map.Entry<String, String> var : variables.entrySet()) {
            template = template.replaceAll(Pattern.quote("$" + var.getKey() + "$"), var.getValue());
        }

        return new Message(template);
    }
}
