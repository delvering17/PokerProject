package DB_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProfileDAO {

	Connection con = null;
	Statement stmt;
	ResultSet rs = null;
	
	String sql = null;
			
	
	
	public ProfileDAO() {
		
		String url = "jdbc:mariadb://localhost:3306/poker_db";
		String username = "pokerserver";
		String password = "cksdnr12";
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			Connection con = DriverManager.getConnection( url, username, password );
			
			stmt = con.createStatement();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<ProfileDTO> profileRead() {
		ArrayList<ProfileDTO> res = new ArrayList<ProfileDTO>();

		sql = "select * from profile";
		
		ResultSet rs;
		
		try {
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { 
				
				ProfileDTO dto = new ProfileDTO();
				dto.profilenum = rs.getInt("profilenum");
				dto.nickname = rs.getString("nickname");
				dto.gender = rs.getString("gender");
				dto.introduce = rs.getString("introduce");
				dto.totalGame = rs.getInt("totalGame");
				dto.win = rs.getInt("win");
				dto.lose = rs.getInt("lose");
				dto.money = rs.getInt("money");
				
				
				res.add(dto);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();		
			
		}
		
		return res;
	}
	
	public ProfileDTO profileReadDetail(int num) {
		ProfileDTO res = null;
		sql = "select * from member where profilenum = "+ num;
		
		ResultSet rs;
		
		try {
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { 
				
				ProfileDTO dto = new ProfileDTO();
				dto.profilenum = rs.getInt("profilenum");
				dto.nickname = rs.getString("nickname");
				dto.gender = rs.getString("gender");
				dto.introduce = rs.getString("introduce");
				dto.totalGame = rs.getInt("totalGame");
				dto.win = rs.getInt("win");
				dto.lose = rs.getInt("lose");
				dto.money = rs.getInt("money");
				
			
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();		
			
		}
		
		return res;
	}
	
	public ProfileDTO profileReadCheck (String str) {
		ProfileDTO res = null;
		sql = "select * from profile where " + str;
		
		ResultSet rs;
		
		try {
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { 
				
				res = new ProfileDTO();
				res.profilenum = rs.getInt("profilenum");
				res.nickname = rs.getString("nickname");
				res.gender = rs.getString("gender");
				res.introduce = rs.getString("introduce");
				res.totalGame = rs.getInt("totalGame");
				res.win = rs.getInt("win");
				res.lose = rs.getInt("lose");
				res.money = rs.getInt("money");
				
			
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();		
			
		}
	
		return res;
	}
	
	
	
	public int profileWrite(ProfileDTO dto) {
		int res = 0 ;

		sql = "insert into profile (nickname,gender,introduce,totalGame,win,lose,money) values "
				+ "('"+dto.nickname	
				+"','"+dto.gender	
				+"','"+dto.introduce
				+"',"+dto.totalGame
				+","+dto.win
				+","+dto.lose
				+","+dto.money
				+");";
		
		System.out.println(sql);
		
		try {
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
		}
		
		return res;
	}
	
	void close() { 
		
		if (rs != null) try { rs.close(); } catch (SQLException e) {}
		if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
		if (con != null) try { con.close(); } catch (SQLException e) {}
		
		
	}
	
	
	
}
