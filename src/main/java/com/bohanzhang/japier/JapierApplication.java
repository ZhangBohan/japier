package com.bohanzhang.japier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JapierApplication {

	public static void main(String[] args) {
		SpringApplication.run(JapierApplication.class, args);
	}

}
