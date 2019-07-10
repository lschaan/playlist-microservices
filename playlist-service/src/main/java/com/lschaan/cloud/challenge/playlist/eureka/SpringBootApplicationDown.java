package com.lschaan.cloud.challenge.playlist.eureka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringBootApplicationDown implements ApplicationListener<ContextClosedEvent> {

	@Autowired
	private EurekaServiceDiscovery eureka;

	@Override
	public void onApplicationEvent(ContextClosedEvent event) {
		eureka.delete();
	}
}
