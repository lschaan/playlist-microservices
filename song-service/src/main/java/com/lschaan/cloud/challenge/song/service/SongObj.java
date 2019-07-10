package com.lschaan.cloud.challenge.song.service;

public class SongObj {
	private String name;
	private int ID;

	public SongObj() {
	}

	public SongObj(String name, int iD) {
		this.name = name;
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

}
