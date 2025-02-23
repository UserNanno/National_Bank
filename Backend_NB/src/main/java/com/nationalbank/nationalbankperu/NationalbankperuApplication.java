package com.nationalbank.nationalbankperu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.nationalbank") // Escaneo explícito de componentes
public class NationalbankperuApplication {

    public static void main(String[] args) {
        SpringApplication.run(NationalbankperuApplication.class, args);
    }
}
