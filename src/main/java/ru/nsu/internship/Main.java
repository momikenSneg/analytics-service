package ru.nsu.internship;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan("ru.nsu.internship.models")
public class Main {
    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("application");
    }
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}

