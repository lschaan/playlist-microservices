package com.lschaan.cloud.challenge.song;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SongApplication {
	public static void main(String[] args) {
		SpringApplication.run(SongApplication.class, args);
	}
}
