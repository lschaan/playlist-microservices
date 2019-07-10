package com.lschaan.cloud.challenge.playlist.controller;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lschaan.cloud.challenge.playlist.service.PlaylistService;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@RestController
public class PlaylistController {

	@RequestMapping("/playlist/{id}")
	public ResponseEntity getPlaylist(@PathVariable("id") Integer id) {
		return new ResponseEntity(PlaylistService.getPlaylist(id), HttpStatus.OK);
	}

	@Bean
	public ServletRegistrationBean servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet(),
				"/hystrix.stream");
		return registration;
	}
}
