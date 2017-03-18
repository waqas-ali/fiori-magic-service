package user.oauth.app.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.appengine.api.utils.SystemProperty;

public class MySQLDatabase {
	private String driverName;
	private String url;
	private String userName;
	private String password;
	private Connection connection;

	public MySQLDatabase() {

	}

	public void init() throws Exception {
		if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
			driverName = "com.mysql.jdbc.GoogleDriver";
			url = "jdbc:google:mysql://your-project-id:your-instance-name/guestbook?user=root";
			userName = "";
			password = "";
		} else {
			driverName = "com.google.appengine.api.rdbms.AppEngineDriver";
			url = "jdbc:google:rdbms://localhost:3306/lang_points";
			userName = "root";
			password = "root";
		}
	}
	public void createConnection() {
		try {
			if ( connection == null ) {
				init();
				Class.forName(driverName);
				connection = DriverManager.getConnection(url,userName,password);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void closeConnection() throws SQLException {
		if ( connection != null ) {
			connection.close();
			if ( connection.isClosed() )
				connection = null;
		}
	}
	public ResultSet executeQuery(String query) throws Exception{
		Statement stmt = connection.createStatement();
		ResultSet result = stmt.executeQuery(query);
		return result;
	}
}
