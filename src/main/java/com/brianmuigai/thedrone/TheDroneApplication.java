package com.brianmuigai.thedrone;

import config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({
		FileStorageProperties.class
})
@EnableScheduling
public class TheDroneApplication {
	public static void main(String[] args) {
		SpringApplication.run(TheDroneApplication.class, args);
	}

}
