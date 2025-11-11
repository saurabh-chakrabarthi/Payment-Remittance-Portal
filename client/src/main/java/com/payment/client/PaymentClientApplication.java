package com.payment.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {R2dbcAutoConfiguration.class})
@ComponentScan(basePackages = "com.payment.client")
public class PaymentClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentClientApplication.class, args);
    }
}
