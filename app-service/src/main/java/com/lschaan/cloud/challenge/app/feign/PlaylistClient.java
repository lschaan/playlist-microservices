package com.lschaan.cloud.challenge.app.feign;

import java.util.List;

import com.lschaan.cloud.challenge.app.service.SongObj;

import feign.Param;
import feign.RequestLine;

public interface PlaylistClient {

	@RequestLine("GET /playlist/{id}")
	public List<SongObj> getPlaylist (@Param("id") Integer id);
}