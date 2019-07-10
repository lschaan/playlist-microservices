package com.lschaan.cloud.challenge.playlist.service;

import java.util.List;

import com.lschaan.cloud.challenge.playlist.command.PlaylistCommand;

public class PlaylistService {
	
	public static List<SongObj> getPlaylist (Integer id) {
		return new PlaylistCommand(id).execute();
	}

}
