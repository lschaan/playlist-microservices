package com.lschaan.cloud.challenge.app.commands;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lschaan.cloud.challenge.app.eureka.EurekaServiceDiscovery;
import com.lschaan.cloud.challenge.app.eureka.ServiceDiscoveryModule;
import com.lschaan.cloud.challenge.app.feign.SongClient;
import com.lschaan.cloud.challenge.app.ribbon.ServerPicker;
import com.lschaan.cloud.challenge.app.service.SongObj;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import feign.Feign;
import feign.gson.GsonDecoder;

public class SongNameCommand extends HystrixCommand<String> {
	private static final Integer MAX_TIMEOUT = 5000;
	private EurekaServiceDiscovery eureka;
	private ApplicationContext applicationContext;

	private Integer id;
	private SongClient songClient;
	private String URL;

	public SongNameCommand(Integer id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(id.toString())).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(MAX_TIMEOUT)));
		this.id = id;
		this.applicationContext = new AnnotationConfigApplicationContext(ServiceDiscoveryModule.class);
		this.eureka = (EurekaServiceDiscovery) applicationContext.getBean("eurekaDiscovery");
	}

	public String run() {
		this.URL = ServerPicker.chooseServer(eureka.getHosts("SONG-SERVICE"));
		this.songClient = Feign.builder().decoder(new GsonDecoder()).target(SongClient.class, URL);
		SongObj song = songClient.getSong(id);

		return song.getName();
	}

	public String getFallback() {
		return "";
	}
}
