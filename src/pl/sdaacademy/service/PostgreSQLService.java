package pl.sdaacademy.service;

import pl.sdaacademy.config.Config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLService implements JDBCService {
	private Config config;
	private Connection connection;

	public PostgreSQLService(Config config) {
		this.config = config;
	}

	@Override
	public Connection connect() {
		connection = getConnection();
		try {
			connection = DriverManager.getConnection(config.getUrl(), config.getUser(), config.getPassword());
			System.out.println("DB Connected!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	public Connection connectWithProperties(){
		connection = getConnection();
		Properties connectionProps = config.getProperties();
		try {
			connection = DriverManager.getConnection(connectionProps.getProperty("url"), connectionProps.getProperty("user"), connectionProps.getProperty("password"));
			System.out.println("DB Connected!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public Connection connectWithPropsFile() {
		connection = getConnection();
		Properties props = new Properties();
		try {
			try (InputStream in = Files.newInputStream(Paths.get("sda.properties")))
			{
				props.load(in);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
      /*
      PrintWriter writer = new PrintWriter("C:\Users\Lucas\jdbc\sda.properties");
      props.store(writer, "comment");
      */
		String drivers = props.getProperty("jdbc.drivers");
		if (drivers != null) System.setProperty("jdbc.drivers", drivers);
		String url = props.getProperty("jdbc.url");
		String username = props.getProperty("jdbc.username");
		String password = props.getProperty("jdbc.password");

		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}

	public Connection getConnection() {
		return this.connection;
	}

	@Override
	public void disconnect() {
		try {
			this.connection.close();
			System.out.println("DB Disconnected!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


}
