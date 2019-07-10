package com.lschaan.cloud.challenge.playlist.eureka;

import org.springframework.beans.factory.annotation.Value;

import feign.Feign;
import feign.gson.GsonDecoder;

public class EurekaServiceDiscovery {
	private EurekaClient client;

	@Value("${eureka.url}")
	private String url;

	@Value("${server.port}")
	private String port;

	@Value("${server.ip}")
	private String ip;

	@Value("${application.name}")
	private String service;

	@Value("${host.name}")
	private String host;

	public void register() {
		createClient();

		String request = "{\n" + "	\"instance\": {\n" + "		\"hostName\": \"" + host + "\",\n" + "		\"app\": \""
				+ service + "\",\n" + "		\"vipAddress\": \"playlist-service\",\n"
				+ "		\"secureVipAddress\": \"playlist-service\",\n" + "		\"ipAddr\": \"" + ip + "\",\n"
				+ "		\"status\": \"UP\",\n" + "		\"port\": {\"$\": \"" + port + "\", \"@enabled\": \"true\"},\n"
				+ "		\"securePort\": {\"$\": \"8443\", \"@enabled\": \"true\"},\n"
				+ "		\"healthCheckUrl\": \"http://" + ip + ":" + port + "/healthcheck\",\n"
				+ "		\"statusPageUrl\": \"http://" + ip + ":" + port + "/status\",\n"
				+ "		\"homePageUrl\": \"http://" + ip + ":" + port + "\",\n" + "		\"dataCenterInfo\": {\n"
				+ "			\"@class\": \"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\", \n"
				+ "			\"name\": \"MyOwn\"\n" + "		}\n" + "	}\n" + "}";

		client.registry(request, service);
		client.updateToUP(service, host);

	}

	public void delete() {
		client.delete(service, host);
	}

	private void createClient() {
		this.client = Feign.builder().decoder(new GsonDecoder()).target(EurekaClient.class, url + "/v2/apps");
	}
}
