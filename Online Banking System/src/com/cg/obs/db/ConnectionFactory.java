package com.cg.obs.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


/**
 * The Class {@code ConnectionFactory} will create an instance whenever called by the user. 
 *
 * @GroupNo : 7
 * 
 * @Author1: Subhana- 46001890
 * @Author2: Atul Anand- 46001684
 * @Author3: Atal Tiwari- 46002419
 * @Author4: Survagya Agarwal- 46002233
 * 
 * @Version- 1.0
 * 
 * @Date-30/10/2019
 * 
 * */  
public class ConnectionFactory {

	private static ConnectionFactory connectionFactory = null;
	private static Properties properties = new Properties();
	private String url;
	private String username;
	private String password;
	private String dbDriver;
	
	public ConnectionFactory() {
		try {
			try (InputStream inputStream = new FileInputStream(".//resources//jdbc.properties");){
				properties.load(inputStream);
				dbDriver = properties.getProperty("db.driver");
				Class.forName(dbDriver);
				}catch(IOException e) {
					e.printStackTrace();
				}
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * To get an instance of connectionFactory whenever called by the user  
	 *
	 * @return {@code connectionFactory} if the present Connection is "null" {@code ConnectionFactory}
     *          equivalent to ConnectionFactory.
	 * 			
	 * 	
	 */
	public static ConnectionFactory getInstance() {
		if(connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}
	public Connection getConnection() throws SQLException{
		Connection conn = null;
		url = properties.getProperty("db.url");
		username = properties.getProperty("db.username");
		password = properties.getProperty("db.password");
		conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
