package com.lschaan.cloud.challenge.app.feign;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface EurekaClient {

	@RequestLine("POST /apps/{service}")
	@Headers("Content-Type: application/json")
	@Body("{jsonString}")
	public void registry(@Param("jsonString") String jsonString, @Param("service") String service);

	@RequestLine("PUT /apps/{service}/{host}/status?value=UP")
	public void updateToUP(@Param("service") String service, @Param("host") String host);

	@RequestLine("DELETE /apps/{service}/{host}")
	public void delete(@Param("service") String service, @Param("host") String host);

	@RequestLine("GET /vips/{service}")
	@Headers("Accept: application/json")
	public String getHosts(@Param("service") String service);

}
