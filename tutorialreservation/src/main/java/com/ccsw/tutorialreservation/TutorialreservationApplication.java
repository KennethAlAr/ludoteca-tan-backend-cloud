package com.ccsw.tutorialreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TutorialreservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialreservationApplication.class, args);
	}

}
