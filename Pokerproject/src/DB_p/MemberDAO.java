package DB_p;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class MemberDAO {
	
	Connection con = null;
	Statement stmt;
	ResultSet rs = null;
	
	String sql = null;
	
	public MemberDAO() {
		
		String url = "jdbc:mariadb://192.168.1.13:3306/poker_db";
		String username = "pokertest";
		String password = "cksdnr12";
		// mysql -u pokertest -p -h 192.168.20.28 
		// password cksdnr12
		// use poker_db;
		// select * from member
		// select * from profile
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			
			Connection con = DriverManager.getConnection( url, username, password );
			
			stmt = con.createStatement();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public ArrayList<MemberDTO> memberRead() {
		ArrayList<MemberDTO> res = new ArrayList<MemberDTO>();
		sql = "select * from member";
		
		ResultSet rs;
		try {
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { 
				
				MemberDTO dto = new MemberDTO();
				dto.membernum = rs.getInt("membernum");
				dto.id = rs.getString("id");
				dto.password = rs.getString("password");
				dto.name = rs.getString("name");
				dto.email = rs.getString("email");
				dto.findPasswordQ = rs.getString("findpasswordQ");
				dto.findPasswordA = rs.getString("findpasswordA");
				dto.signDate = rs.getTimestamp("signdate");
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
	
	public MemberDTO memberReadDetail(int num) {
		MemberDTO res = null;
		sql = "select * from member where  = "+ num;
		
		ResultSet rs;
		
		try {
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { 
				
				MemberDTO dto = new MemberDTO();
				dto.membernum = rs.getInt("membernum");
				dto.id = rs.getString("id");
				dto.password = rs.getString("password");
				dto.name = rs.getString("name");
				dto.email = rs.getString("email");
				dto.findPasswordQ = rs.getString("findpasswordQ");
				dto.findPasswordA = rs.getString("findpasswordA");
				dto.signDate = rs.getTimestamp("signdate");
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();		
			
		}
		
		return res;
	}
	
	public MemberDTO memberReadCheck(String str) {
		MemberDTO res = null;
		sql = "select * from member where "+ str;
		
		ResultSet rs;
		
		try {
			
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) { 
				
				res = new MemberDTO();
				res.membernum = rs.getInt("membernum");
				res.id = rs.getString("id");
				res.password = rs.getString("password");
				res.name = rs.getString("name");
				res.email = rs.getString("email");
				res.findPasswordQ = rs.getString("findpasswordQ");
				res.findPasswordA = rs.getString("findpasswordA");
				res.signDate = rs.getTimestamp("signdate");
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();		
			
		}
		
		return res;
	}
	
	public int memberWrite(MemberDTO dto) {
		int res = 0  ;
		
		sql = "insert into member (id,password,name,email,findpasswordQ,findpasswordA,signdate)values "
			+ "('"+dto.id
			+"','"+dto.password
			+"','"+dto.name
			+"','"+dto.email
			+"','"+dto.findPasswordQ
			+"','"+dto.findPasswordA
			+"', sysdate() "
			+");";
		
		
		// profile연결 필요
	
		// 나중에 삭
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
