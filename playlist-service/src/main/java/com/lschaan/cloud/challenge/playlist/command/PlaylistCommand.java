package com.lschaan.cloud.challenge.playlist.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lschaan.cloud.challenge.playlist.connection.DBConfig;
import com.lschaan.cloud.challenge.playlist.connection.DBConnection;
import com.lschaan.cloud.challenge.playlist.service.SongObj;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class PlaylistCommand extends HystrixCommand<List<SongObj>> {
	private static final Integer MAX_TIMEOUT = 5000;
	private ApplicationContext applicationContext;
	private Integer id;

	public PlaylistCommand(Integer id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(id.toString())).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(MAX_TIMEOUT)));
		this.id = id;
		this.applicationContext = new AnnotationConfigApplicationContext(DBConfig.class);
	}

	public List<SongObj> run() throws SQLException {
		List<SongObj> playlist = new ArrayList<>();
		String command = "SELECT * FROM playlists WHERE idPlaylist=?;";

		Connection con = ((DBConnection) applicationContext.getBean("connection")).getConnection();
		PreparedStatement stmt = con.prepareStatement(command);

		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			playlist.add(new SongObj(rs.getInt("idSong")));
		}

		return playlist;
	}

	public List<SongObj> getFallback() {
		return new ArrayList<SongObj>();
	}

}
