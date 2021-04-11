package ru.nsu.internship.service;

import ru.nsu.internship.data.Message;
import ru.nsu.internship.models.VarType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MessageMaker {
    private static final String dateFormat = "dd/mm/yyyy";

    public static Message make(String template, Map<String, String> variables, Map<String, String> types) {

        for (Map.Entry<String, String> var : variables.entrySet()) {
            String type = null;
            if (types != null)
                type = types.get(var.getKey());
            if (type == null){
                template = template.replaceAll(Pattern.quote("$" + var.getKey() + "$"), var.getValue());
            } else {
                if (checkType(var.getValue(), type)){
                    template = template.replaceAll(Pattern.quote("$" + var.getKey() + "$"), var.getValue());
                } else {
                    throw new IllegalArgumentException("Type does not match value");
                }
            }

        }

        return new Message(template);
    }

    private static boolean checkType(String var, String type) {
        try {
            switch (type){
                case "int":
                    Integer.parseInt(var);
                    break;
                case "date":
                    new SimpleDateFormat(dateFormat).parse(var);
                    break;
                case "time":
                    LocalTime.parse(var);
                    break;
                case "double":
                    Double.parseDouble(var);
                    break;
                default:
                    if (!type.equals("string"))
                        throw new IllegalArgumentException("No such type");
            }
        } catch (NumberFormatException | ParseException | DateTimeParseException e){
            return false;
        }
        return true;
    }
}
