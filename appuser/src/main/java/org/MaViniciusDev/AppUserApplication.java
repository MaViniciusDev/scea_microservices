package org.MaViniciusDev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AppUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppUserApplication.class, args);
    }
}