package com.lschaan.cloud.challenge.app.commands;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lschaan.cloud.challenge.app.eureka.EurekaServiceDiscovery;
import com.lschaan.cloud.challenge.app.eureka.ServiceDiscoveryModule;
import com.lschaan.cloud.challenge.app.feign.PlaylistClient;
import com.lschaan.cloud.challenge.app.ribbon.ServerPicker;
import com.lschaan.cloud.challenge.app.service.SongObj;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import feign.Feign;
import feign.gson.GsonDecoder;

public class PlaylistCommand extends HystrixCommand<List<SongObj>> {
	private static final Integer MAX_TIMEOUT = 5000;
	private ApplicationContext applicationContext;
	private EurekaServiceDiscovery eureka;

	private Integer id;
	private String URL;
	private PlaylistClient playlistClient;

	public PlaylistCommand(Integer id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(id.toString())).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(MAX_TIMEOUT)));
		this.id = id;
		this.applicationContext = new AnnotationConfigApplicationContext(ServiceDiscoveryModule.class);
		this.eureka = (EurekaServiceDiscovery)applicationContext.getBean("eurekaDiscovery");

	}

	public List<SongObj> run() {
		this.URL = ServerPicker.chooseServer(eureka.getHosts("PLAYLIST-SERVICE"));
		this.playlistClient = Feign.builder().decoder(new GsonDecoder()).target(PlaylistClient.class, URL);

		List<SongObj> playlist = playlistClient.getPlaylist(id);

		for (SongObj song : playlist) {
			song.setName(new SongNameCommand(song.getId()).execute());
		}

		return playlist;
	}

	public List<SongObj> getFallback() {
		return new ArrayList<SongObj>();
	}

}