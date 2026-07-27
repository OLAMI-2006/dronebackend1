package com.example.dronemanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync// for cuncorent background threads
@EnableScheduling// for internal task schedule
public class  DronemanagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DronemanagementApplication.class, args);
    }

}
