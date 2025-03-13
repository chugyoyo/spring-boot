package com.chugyoyo.debug;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.chugyoyo.debug.*")
public class DebugProvider {

	public static void main(String[] args) {
		SpringApplication.run(DebugProvider.class, args);
	}
}