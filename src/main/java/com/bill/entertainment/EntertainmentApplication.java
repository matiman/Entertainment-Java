package com.bill.entertainment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.bill.entertainment.dao")
@EntityScan( basePackages = {"com.bill.entertainment.entity"})
public class EntertainmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(EntertainmentApplication.class, args);
    }

}
