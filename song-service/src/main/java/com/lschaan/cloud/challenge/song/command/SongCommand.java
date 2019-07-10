package com.lschaan.cloud.challenge.song.command;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.lschaan.cloud.challenge.song.connection.DBConfig;
import com.lschaan.cloud.challenge.song.connection.DBConnection;
import com.lschaan.cloud.challenge.song.service.SongObj;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class SongCommand extends HystrixCommand<SongObj> {
	private static final Integer MAX_TIMEOUT = 5000;
	private ApplicationContext applicationContext;
	private int id;

	public SongCommand(Integer id) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(id.toString())).andCommandPropertiesDefaults(
				HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(MAX_TIMEOUT)));
		this.id = id;
		applicationContext = new AnnotationConfigApplicationContext (DBConfig.class);
	}

	public SongObj run() throws SQLException {
		String command = "SELECT * FROM songs WHERE ID=?;";

		Connection con = ((DBConnection)applicationContext.getBean("connection")).getConnection();
		PreparedStatement stmt = con.prepareStatement(command);

		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			return new SongObj(rs.getString("name"), rs.getInt("ID"));
		}

		throw new RuntimeException();

	}

	public SongObj getFallback() {
		return new SongObj();
	}
}
