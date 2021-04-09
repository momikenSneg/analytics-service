package ru.nsu.internship;

import ru.nsu.internship.data.Message;
import ru.nsu.internship.models.VarType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Utils {
    private static final String dateFormat = "dd/MM/yyyy";

    public static Message makeMessage(String template, Map<String, String> variables, List<VarType> types){

        for (Map.Entry<String, String> var : variables.entrySet()) {
            VarType type = types.stream()
                    .filter(varType -> var.getKey().equals(varType.getName()))
                    .findAny()
                    .orElse(null);
            if (type == null){
                template = template.replaceAll(Pattern.quote("$" + var.getKey() + "$"), var.getValue());
            } else {
                if (checkType(var.getValue(), type.getType())){
                    template = template.replaceAll(Pattern.quote("$" + var.getKey() + "$"), var.getValue());
                } else {
                    //TODO Throw Exception
                }
            }

        }

        return new Message(template);
    }

    //TODo исключения
    private static boolean checkType(String var, String type){
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
                //TODO бросать исключение что неподдерживаемый тип данных
                default:
                    if (!type.equals("string"))
                        return false;
            }
        } catch (NumberFormatException | ParseException | DateTimeParseException e){
            return false;
        }
        return true;
    }
}
