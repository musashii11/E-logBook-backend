package com.codewithmusashi.elogbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


@SpringBootApplication

//@EntityScan(basePackages = {
//        "com.codewithmusashi.elogbook.entity",
//})


public class ELogBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(ELogBookApplication.class, args);
    }

}
