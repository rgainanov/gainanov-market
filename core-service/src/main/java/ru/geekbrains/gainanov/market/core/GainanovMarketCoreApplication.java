package ru.geekbrains.gainanov.market.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GainanovMarketCoreApplication {

    // integration of cart microservice
    // integration url from yml file
    // frontend in separate ms
    // token get into separate ms
    public static void main(String[] args) {
        SpringApplication.run(GainanovMarketCoreApplication.class, args);
    }

}
