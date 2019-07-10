package com.lschaan.cloud.challenge.app.eureka;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lschaan.cloud.challenge.app.feign.EurekaClient;

import feign.Feign;
import feign.gson.GsonDecoder;

public class EurekaServiceDiscovery {
	private EurekaClient eurekaClient;

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
		this.eurekaClient = Feign.builder().decoder(new GsonDecoder()).target(EurekaClient.class, url + "/v2/");

		String request = "{\n" + "	\"instance\": {\n" + "		\"hostName\": \"" + host + "\",\n" + "		\"app\": \""
				+ service + "\",\n" + "		\"vipAddress\": \"app-service\",\n"
				+ "		\"secureVipAddress\": \"app-service\",\n" + "		\"ipAddr\": \"" + ip + "\",\n"
				+ "		\"status\": \"UP\",\n" + "		\"port\": {\"$\": \"" + port + "\", \"@enabled\": \"true\"},\n"
				+ "		\"securePort\": {\"$\": \"8443\", \"@enabled\": \"true\"},\n"
				+ "		\"healthCheckUrl\": \"http://" + ip + ":" + port + "/healthcheck\",\n"
				+ "		\"statusPageUrl\": \"http://" + ip + ":" + port + "/status\",\n"
				+ "		\"homePageUrl\": \"http://" + ip + ":" + port + "\",\n" + "		\"dataCenterInfo\": {\n"
				+ "			\"@class\": \"com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo\", \n"
				+ "			\"name\": \"MyOwn\"\n" + "		}\n" + "	}\n" + "}";

		eurekaClient.registry(request, service);
		eurekaClient.updateToUP(service, host);

	}

	public List<String> getHosts(String name) {
		List<String> hostList = new ArrayList<>();
		JsonArray instanceList = (JsonArray) ((JsonObject) ((JsonArray) ((JsonObject) getJsonEureka(name)
				.get("applications")).get("application")).get(0)).get("instance");

		for (int i = 0; i < instanceList.size(); i++) {
			hostList.add(((String) ((JsonObject) instanceList.get(i)).get("homePageUrl").toString()).replace("\"", ""));
		}
		return hostList;
	}

	public void delete() {
		eurekaClient.delete(service, host);
	}

	private JsonObject getJsonEureka(String name) {
		EurekaClient hostClient = Feign.builder().target(EurekaClient.class, url + "/v2/");
		JsonObject hostObj = new JsonParser().parse(hostClient.getHosts(name)).getAsJsonObject();

		return hostObj;
	}
}
