package com.lschaan.cloud.challenge.app.feign;

import com.lschaan.cloud.challenge.app.service.SongObj;

import feign.Param;
import feign.RequestLine;

public interface SongClient {

	@RequestLine("GET /song/{id}")
	public SongObj getSong (@Param("id") Integer id);
}
