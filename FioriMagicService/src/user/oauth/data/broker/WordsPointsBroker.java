package user.oauth.data.broker;

import java.sql.ResultSet;
import java.util.ArrayList;

import user.oauth.app.databases.MySQLDatabase;
import user.oauth.jpa.model.Words;

public class WordsPointsBroker {

	private final String tableName = "words";
	
	public String pointsCalculation(String sentence) {
		String[] list = sentence.split("\\s+");
		String s = "";
		for ( int i =0; i<list.length; i++ ) {
			s += "'"+list[i]+"',";
		}
		s = s.substring(0,s.length()-1);
		String query = "select category, sum(point) as points from words ";
		query += "where word in ( " + s +" ) ";
		query += "group by category order by points desc limit 1";
		
		MySQLDatabase db = new MySQLDatabase();
		String result = "";
		try {
			db.createConnection();
			ResultSet rs = db.executeQuery(query);
			String cat = "";
			double points = 0;
			if ( rs.next()) {
				cat = rs.getString(1);
				points = rs.getDouble(2);
			}
			result = cat +"  " + points;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try{
			db.closeConnection();
			}catch(Exception e) {}
		}
		
		return result;
	
	}
	
	
	public ArrayList<Words> getList() {
		return null;
	}
	public void insert(Words obj) {
		
	}
	public boolean update(Words obj) {
		return true;
	}
	public boolean delete(Words obj) {
		return false;
	}
	
}
