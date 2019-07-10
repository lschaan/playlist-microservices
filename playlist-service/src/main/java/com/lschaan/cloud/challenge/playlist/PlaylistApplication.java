package com.lschaan.cloud.challenge.playlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PlaylistApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PlaylistApplication.class);
	}
}
