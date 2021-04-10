package ru.nsu.internship;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.nsu.internship.data.Report;
import ru.nsu.internship.data.Subscription;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
    public static void main(String[] args) {
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

        Map<Subscription, String> subs = new HashMap<>();
        Subscription a = new Subscription();
        Subscription b = new Subscription();

        List<String> urls1 = new ArrayList<>();
        List<String> urls2 = new ArrayList<>();

        urls1.add("https://httpbin.org/post");
        urls1.add("https://postman-echo.com/post");
        urls1.add("https://vk.com");

        a.setMessage("The meeting will take place in the Room No.5 at 11 o'clock");
        a.setUrls(urls1);

        urls2.add("https://vk.com");
        urls2.add("https://httpbin.org/post");
        urls2.add("https://postman-echo.com/post");

        b.setMessage("The meeting will take place in the Room No.5 at 11 o'clock");
        b.setUrls(urls2);

        subs.put(a, "NICHESE");

        System.out.println(subs.get(b));


        if (b.equals(a))
            System.out.println("WOW");
        else
            System.out.println("PIZDA");


    }

}
