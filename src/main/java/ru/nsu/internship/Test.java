package ru.nsu.internship;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.internship.data.Report;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String args[]) {
//        String name = "\\$";
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("\\$");
//        stringBuilder.append(name);
//        stringBuilder.append("\\$");
//        String regex = "\\$\\*\\$";
//        String rep = "Bunny";
//        String myStr =input.replaceAll(regex, rep);


//        Pattern pattern = Pattern.compile("\\$.\\$");
//        Matcher matcher = pattern.matcher(input);
//        boolean found = matcher.matches();
//
//        while (matcher.find()){
//            System.out.println(matcher.group());
//        }

       // Pattern pattern = Pattern.compile(reg);
        //Matcher matcher = pattern.matcher(name);
        //boolean found = matcher.matches();

//        StringBuilder newName = new StringBuilder(); // = newName = name.replaceAll("\\.", "\\\\\\\\.");
//        newName.append("\\\\$");

//        while (matcher.find()) {
//            String regex = Matcher.quoteReplacement("\\") + matcher.group();
//
//            System.out.println(regex + ' ' + Matcher.quoteReplacement("\\") + matcher.group());
//            newName = name.replaceAll("\\$", Matcher.quoteReplacement("\\") + matcher.group());
//        }
//
//        System.out.println(input);
//
//        for (char ch: name.toCharArray()){
//            if (escapes.indexOf(ch) != -1){
//                newName.append("\\\\");
//            }
//            if (ch == '\\'){
//                newName.append("\\\\\\");
//            }
//            newName.append(ch);
//        }
//
//        newName.append("\\\\$");
//        System.out.println(newName.toString());
//
//        Pattern.quote("$" + ".+*?$^()[]{}|" + "$");

        //String reg = "\\$\\.\\+\\*\\?\\$\\^\\(\\)\\[]\\{}\\|\\\\\\$";

//        String input = "Jetbrains Internship in $.+*?$^()[]{}|\\$ team.";
//        String name = ".+*?$^()[]{}|\\";
//        String variable = "Bunny";
//
//        input = input.replaceAll(Pattern.quote("$" + name + "$"), "Bunny");
//        System.out.println(input);
        Gson gson = new GsonBuilder().create();
        Report report = new Report();
        report.setTemplateId("Id");
        Map<String, String> vars = new HashMap<>();
        vars.put("place", "Room No.5");
        vars.put("time", "11");
        report.setVariables(vars);

        String json = gson.toJson(report);

        System.out.println(json);

    }

}
