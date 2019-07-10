package com.lschaan.cloud.challenge.song.eureka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource ("classpath:application.properties")
public class ServiceDiscoveryModule {
	@Bean
	public EurekaServiceDiscovery eurekaDiscovery() {
		return new EurekaServiceDiscovery();
	}
}
