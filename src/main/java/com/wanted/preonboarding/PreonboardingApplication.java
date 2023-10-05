package com.wanted.preonboarding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PreonboardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(PreonboardingApplication.class, args);
	}

}
