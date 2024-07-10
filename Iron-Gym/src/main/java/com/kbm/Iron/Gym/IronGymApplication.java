package com.kbm.Iron.Gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class IronGymApplication {

	public static void main(String[] args) {
		SpringApplication.run(IronGymApplication.class, args);
	}

}
