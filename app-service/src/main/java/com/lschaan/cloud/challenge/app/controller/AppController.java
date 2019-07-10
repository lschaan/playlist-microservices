package com.lschaan.cloud.challenge.app.controller;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lschaan.cloud.challenge.app.service.AppService;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

@RestController
public class AppController {

	@RequestMapping("/app/{id}")
	public ResponseEntity returnPlaylist(@PathVariable("id") Integer id) {
		return new ResponseEntity(AppService.getPlaylist(id), HttpStatus.OK);
	}

	@Bean
	public ServletRegistrationBean servletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new HystrixMetricsStreamServlet(),
				"/hystrix.stream");
		return registration;
	}

}
