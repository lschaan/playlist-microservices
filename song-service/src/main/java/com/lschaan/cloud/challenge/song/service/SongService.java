package com.lschaan.cloud.challenge.song.service;

import com.lschaan.cloud.challenge.song.command.SongCommand;

public class SongService {

	public static SongObj getSong(Integer id) {
		return new SongCommand(id).execute();
	}

}
