package com.lschaan.cloud.challenge.app.ribbon;

import java.util.ArrayList;
import java.util.List;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

public class ServerPicker {

	public static String chooseServer(List<String> hosts) {
		ILoadBalancer loadBalancer = new BaseLoadBalancer();
		List<Server> servers = new ArrayList<Server>();

		for (String host : hosts)
			servers.add(new Server(host));

		loadBalancer.addServers(servers);

		Server server = loadBalancer.chooseServer(null);
		return "http://" + server.getHost() + ":" + server.getPort();
	}

}
