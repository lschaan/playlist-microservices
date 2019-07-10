package com.lschaan.cloud.challenge.app.service;

import java.util.List;

import com.lschaan.cloud.challenge.app.commands.PlaylistCommand;

public class AppService {

	public static List<SongObj> getPlaylist(Integer id) {
		return new PlaylistCommand(id).execute();
	}

}
