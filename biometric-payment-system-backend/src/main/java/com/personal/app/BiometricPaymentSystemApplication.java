package com.personal.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BiometricPaymentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BiometricPaymentSystemApplication.class, args);
	}

}
