package com.lschaan.cloud.challenge.song.eureka;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface EurekaClient {

	@RequestLine("POST /{service}")
	@Headers("Content-Type: application/json")
	@Body("{jsonString}")
	public void registry(@Param("jsonString") String jsonString, @Param("service") String service);

	@RequestLine("PUT /{service}/{host}/status?value=UP")
	public void updateToUP(@Param("service") String service, @Param("host") String host);

	@RequestLine("DELETE /{service}/{host}")
	public void delete(@Param("service") String service, @Param("host") String host);

}
