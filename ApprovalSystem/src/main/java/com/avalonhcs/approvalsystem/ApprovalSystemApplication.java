package com.avalonhcs.approvalsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.avalonhcs.approvalsystem.model"})
public class ApprovalSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApprovalSystemApplication.class, args);
    }

}
