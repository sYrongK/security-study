package com.study.ant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(scanBasePackages = "com.study.ant")
//@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class AntApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntApplication.class, args);
    }

}
