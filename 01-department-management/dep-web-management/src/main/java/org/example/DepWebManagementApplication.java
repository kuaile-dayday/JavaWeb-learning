package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan // 添加这个注解后，会自动扫描到DemoFilter
@SpringBootApplication
public class DepWebManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DepWebManagementApplication.class, args);
    }

}
