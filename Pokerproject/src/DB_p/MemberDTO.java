package DB_p;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberDTO {
	int membernum ;
	String id;
	String password;
	String name;
	String email;

	String findPasswordQ;
	String findPasswordA;
	Date signDate;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public MemberDTO(int membernum, String id, String password, String name, String email,  String findpasswordQ,
			String findPasswordA, String signDate) {
		super();
		this.membernum = membernum;
		this.id = id;
		this.password = password;
		this.name = name;
		this.email = email;
	
		this.findPasswordQ = findpasswordQ;
		this.findPasswordA = findPasswordA;
		
		try {
			this.signDate = sdf.parse(signDate);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public MemberDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getMembernum() {
		return membernum;
	}

	public void setMembernum(int membernum) {
		this.membernum = membernum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getFindPasswordQ() {
		return findPasswordQ;
	}

	public void setFindpasswordQ(String findPasswordQ) {
		this.findPasswordQ = findPasswordQ;
	}

	public String getFindPasswordA() {
		return findPasswordA;
	}

	public void setFindPasswordA(String findPasswordA) {
		this.findPasswordA = findPasswordA;
	}
	
	public String getsignDate() {
		return sdf.format(signDate);
	}

	public void setsignDate(Date signDate) {
		this.signDate = signDate;
	}

}

