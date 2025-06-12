package org.nerdwire.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NerdWireApplication {
    public static void main(String[] args) {
        SpringApplication.run(NerdWireApplication.class, args);
    }
}
