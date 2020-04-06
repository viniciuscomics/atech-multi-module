package com.atech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AtechServerRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtechServerRegisterApplication.class, args);
	}

}
