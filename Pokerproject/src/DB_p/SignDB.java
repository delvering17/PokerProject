package DB_p;

import net_p.MyData;
import net_p.TCPData;

public class SignDB {

	public SignDB() {
		
	}
	
	public void DBmemWrite(String id, String password, String name, String email, String findpasswordQ,
			String findPasswordA, String signDate) {
		MemberDTO dto = new MemberDTO(1, id, password, name, email, findpasswordQ, findPasswordA, signDate);
		System.out.println(new MemberDAO().memberWrite(dto));
	}

	public void DBproWrite(String nickname, String gender, String introduce, int totalGame, int win, int lose,
			int money) {
		
		ProfileDTO dto = new ProfileDTO(1, nickname, gender, introduce, totalGame, win, lose, money);
		System.out.println(new ProfileDAO().profileWrite(dto));
	}
	
//	public void DBmemRead(String id) {
//		
//		System.out.println(new MemberDAO().memberReadCheck(id));
//		
//	}
	
	public boolean DBmemCheck(String id) {
		
		if (new MemberDAO().memberReadCheck(id) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean DBproCheck(String id) {
		
		if (new ProfileDAO().profileReadCheck(id) == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public String login_check(String id) {
		
		return new MemberDAO().memberReadCheck(id).password ;
		
	}

	public String FindID_check(String name) {
		
		return new MemberDAO().memberReadCheck(name).email ;
		
	}

	public String FindID_get(String name) {
		
		return new MemberDAO().memberReadCheck(name).id ;
		
	}

	public String FindPW_Qcheck(String id) {
		
		return new MemberDAO().memberReadCheck(id).findPasswordQ ;
		
	}
	public String FindPW_Acheck(String id) {
		
		return new MemberDAO().memberReadCheck(id).findPasswordA ;
		
	}

	public String FindPW_get(String id) {
		
		return new MemberDAO().memberReadCheck(id).password ;
		
	}
	
	public int num_get(String id) {
		
		int a = new MemberDAO().memberReadCheck(id).membernum;
		return a;
		
	}
	
	public ProfileDTO num_profileRead(String id) {

		return new ProfileDAO().profileReadCheck(id);
		
	}
	
	public void resInsert(MyData myData) {
		
		new ProfileDAO().profileModify(myData);
	}
}
