package login_p;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB_p.SignDB;




class Gen_button_login extends JButton implements ActionListener {
	Login_frame login_frame;
	String function;
	public Gen_button_login(Login_frame login_frame,String name, String function, int dis_x, int dis_y, int size_x, int size_y) {
		super(name);
		this.login_frame = login_frame;
		setBounds(dis_x, dis_y, size_x, size_y);
		this.function = function;
		
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			Inter_button_login inter_button_login = (Inter_button_login)Class.forName("login_p."+function).newInstance();
			inter_button_login.go(login_frame);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
	}
	
	
}


interface Inter_button_login {
	
	public void go(Login_frame login_frame);
}

class LogIn_in implements Inter_button_login {

	@Override
	public void go(Login_frame login_frame) {
		
		
		String id_login = login_frame.login_panel.id_login.getText().trim() ;
		String pw_login = new KorEng().con(login_frame.login_panel.pw_login.getText().trim() );
		
		boolean res = false ;
		boolean respw = false;
		res = new SignDB().DBmemCheck("id = '"+
				id_login + "';") ;
		
		if (new GongbackCon().con(id_login) || new GongbackCon().con(pw_login) ) {
			System.out.println("빈칸을 채워주세요");
		} else {
			if (res) {
				
				respw = new SignDB().login_check("id = '"+
						id_login + "';").equals(pw_login);
				if (respw) {
					// 성공 시 대기실 화면 전환
					System.out.println("로그인 성공");
				} else {
					System.out.println("로그인 실패");
				}
				
				
			} else {
				System.out.println("정보가 맞지 않습니다");
			}

		
		}
		
	}
	
} 

class SignIn_in implements Inter_button_login {
	Signin_panel signin_panel;
	@Override
	public void go(Login_frame login_frame) {
		signin_panel = new Signin_panel(login_frame);
		login_frame.add(signin_panel);
		login_frame.signin_panel = this.signin_panel;
		login_frame.remove(login_frame.login_panel);
		login_frame.repaint();
	}
	

	
} 

class FindInfo_in implements Inter_button_login {
	FindIDPW_panel findIDPW_panel;
	@Override
	public void go(Login_frame login_frame) {
		
		findIDPW_panel = new FindIDPW_panel(login_frame);
		login_frame.add(findIDPW_panel);
		login_frame.findIDPW_panel = this.findIDPW_panel;
		login_frame.remove(login_frame.login_panel);
		login_frame.repaint();
		
		
	}

} 

class SignInComplete_in implements Inter_button_login {
	
	Login_frame login_frame;
	Login_panel login_panel;
	
	String id;
	String password;
	String passwordCon;
	String name;
	String email;
	String findPasswordQ;
	String findPasswordA;
	String signDate;
	
	String nickname;
	String gender;
	String introduce;
	int totalGame;
	int win;
	int lose;
	int money;
	
	ArrayList<Gen_textfiled> signInfo ;
	ArrayList<Textfiled_password> pwInfo ;
	@Override
	public void go(Login_frame login_frame)  {
		this.login_frame = login_frame;
		this.signInfo = login_frame.signin_panel.signInfo;
		this.pwInfo = login_frame.signin_panel.pwInfo;
		
		id = signInfo.get(0).getText().trim(); 
		name = signInfo.get(1).getText().trim(); 
		email = signInfo.get(2).getText().trim(); 
		findPasswordQ = signInfo.get(5).getText().trim(); 
		findPasswordA = signInfo.get(6).getText().trim();
		
		password = new KorEng().con(pwInfo.get(0).getText().trim());
		passwordCon = new KorEng().con(pwInfo.get(1).getText().trim());
	
		
		signDate = "2022-01-01";
		
		nickname = signInfo.get(3).getText().trim();
		gender = signInfo.get(4).getText().trim();
		introduce = signInfo.get(7).getText().trim();
		totalGame = 0;
		win = 0;
		lose = 0;
		money = 1000000; 
		
		boolean resA = false;
		boolean resB = false;
		boolean resGongback = false;
		String Gong = "";
		for (int i = 0 ; i < 7 ; i++) {
			if (new GongbackCon().sicon(signInfo.get(i).getText())) {
				Gong = signInfo.get(i).getName();
			resGongback = true;
			break;
			} else {}
		}
		for (Gen_textfiled gt  : signInfo) {
			resA = new GongbackCon().con(gt.getText().trim());
		}
		for (Textfiled_password tp  : pwInfo) {
			resB = new GongbackCon().con(tp.getText().trim());
		}
		
		if (resA || resB) {
			System.out.println("빈칸을 채워주세요");
		
		} else if (resGongback) {
			System.out.println(Gong +"의 공백을 제외하고 입력해주세요.");
			
		} else if (!password.equals(passwordCon)) {
			System.out.println("비밀번호와 확인이 일치하지 않습니다.");
		} else if (login_frame.signin_panel.doubleCheck_id) {
			System.out.println("아이디 중복 체크를 해주세요");
		} else if (login_frame.signin_panel.doublecCheck_nickname) {
			System.out.println("닉네임 중복 체크를 해주세요");
		} else {
			System.out.println("회원가입완료");
			complete();
		}
		
	}
	
	public void complete() {
		login_panel = new Login_panel(login_frame);
		login_frame.add(login_panel);
		login_frame.login_panel = this.login_panel;
		login_frame.remove(login_frame.signin_panel);
		login_frame.repaint();
		

		new SignDB().DBmemWrite(id, password,name,email,findPasswordQ,findPasswordA,signDate);
		new SignDB().DBproWrite(nickname,gender,introduce,totalGame,win,lose,money);
		
	}
	
} 

class SignInCancel_in implements Inter_button_login {
	Login_panel login_panel;
	@Override
	public void go(Login_frame login_frame) {
		
		login_panel = new Login_panel(login_frame);
		login_frame.add(login_panel);
		login_frame.login_panel = this.login_panel;
		login_frame.remove(login_frame.signin_panel);
		login_frame.repaint();
		
	}
	
} 

class FindCancel_in implements Inter_button_login {
	Login_panel login_panel;
	@Override
	public void go(Login_frame login_frame) {
		
		login_panel = new Login_panel(login_frame);
		login_frame.add(login_panel);
		login_frame.login_panel = this.login_panel;
		login_frame.remove(login_frame.findIDPW_panel);
		login_frame.repaint();
		
	}
	
} 




class SignIn_ID_DoubleCheck implements Inter_button_login {
	Login_panel login_panel;
	@Override
	public void go(Login_frame login_frame) {

		String str = login_frame.signin_panel.signInfo.get(0).getText().trim() ;
		
		if (new GongbackCon().con(str)) {
			System.out.println("아이디를 입력해주세요");
		} else {
			boolean res = true;
			res = new SignDB().DBmemCheck("id = '"+
			str + "';");
			
			if (res) {
				System.out.println("중복된 아이디");
				
			} else {
				System.out.println("아이디 사용가능");
				login_frame.signin_panel.doubleCheck_id = false;
			}
		}
		
		
	}
} 

class SignIn_Nickname_DoubleCheck implements Inter_button_login {
	Login_panel login_panel;
	@Override
	public void go(Login_frame login_frame) {
		String str = login_frame.signin_panel.signInfo.get(3).getText().trim();
		
		if (new GongbackCon().con(str)) {
			System.out.println("닉네임을 입력해주세요");
			
		} else {
			boolean res = true;
			res = new SignDB().DBproCheck("nickname = '"+
			str + "';");
			
			if (res) {
				System.out.println("중복된 닉네임");
				
			} else {
				System.out.println("닉네임 사용가능");
				login_frame.signin_panel.doublecCheck_nickname = false;
			}
		
		}
	}
	
} 


class FindID_button implements Inter_button_login {

	@Override
	public void go(Login_frame login_frame) {
		
		
		String name_find = login_frame.findIDPW_panel.name_find.getText().trim() ;
		String email_find = login_frame.findIDPW_panel.email_find.getText().trim();
		
		boolean res = false ;
		boolean respw = false;
		res = new SignDB().DBmemCheck("name = '"+
				name_find + "';") ;
		
		if (new GongbackCon().con(name_find) || new GongbackCon().con(email_find) ) {
			System.out.println("빈칸을 채워주세요");
		} else {
			if (res) {
				
				respw = new SignDB().FindID_check("name = '"+
						name_find + "';").equals(email_find);
				if (respw) {
					// 
					JOptionPane.showMessageDialog(null, "아이디는  "+ new SignDB().FindID_get("name = '"+
						name_find + "';") +" 입니다" 
					, "아이디 찾기 성공" , JOptionPane.INFORMATION_MESSAGE);
					
				} else {
					System.out.println("해당되는 정보가 없습니다");
				}
				
				
			} else {
				System.out.println("해당되는 정보가 없습니다");
			}

		
		}
		
	}
	
}

class FindPW_button implements Inter_button_login {

	@Override
	public void go(Login_frame login_frame) {
		
		
		String idpw_find = login_frame.findIDPW_panel.idpw_find.getText().trim() ;
		
		
		boolean res = false ;
		boolean respw = false;
		res = new SignDB().DBmemCheck("id = '"+
				idpw_find + "';") ;
		
		if (new GongbackCon().con(idpw_find) ) {
			System.out.println("빈칸을 채워주세요");
		} else {
			if (res) {
				String aa = "";
				
				aa = JOptionPane.showInputDialog(null, new SignDB().FindPW_Qcheck("id = '"+
						idpw_find + "';"), "답변을 입력하세요", JOptionPane.QUESTION_MESSAGE) +"";
				System.out.println(aa);
				System.out.println();
				if (aa.equals(new SignDB().FindPW_Acheck("id = '"+
						idpw_find + "';"))) {
					JOptionPane.showMessageDialog(null, "비밀번호는  "+ new SignDB().FindPW_get("id = '"+
							idpw_find + "';") +" 입니다" 
							, "아이디 찾기 성공" , JOptionPane.INFORMATION_MESSAGE);
				} else {
					System.out.println("답이 맞지 않습니다");

		

				}

		
			} else {
				System.out.println("정보가 맞지 않습니다");
			}
		
		}
	
	}
}

class GongbackCon {
	
	public GongbackCon() {
		
	}
	
	boolean con (String str) {
		boolean res = false;
		if (str.equals("")) {
			res = true;
			
		} else {
			
		}
		
		return res ;
		
	}
	
	boolean sicon (String str) {
		boolean res = false;
		char [] arr = str.toCharArray();
		for (char a : arr) {
			if ( a == ' ' ) {
				res= true;
				break;
			}
		}
		
		return res; 
	}
}

class KorEng {
	
	public KorEng() {
		
	}
	
	public String con(String str) {
		String result = "";
		String eng = "abcdefghijklmnopqrstuvwxyzqwertop";
		
		String kor = "ㅁㅠㅊㅇㄷㄹㅎㅗㅑㅓㅏㅣㅡㅜㅐㅔㅂㄱㄴㅅㅕㅍㅈㅌㅛㅋㅃㅉㄸㄲㅆㅒㅖ";
		
		char[] engArr = eng.toCharArray();
		char[] korArr = kor.toCharArray();
		
		char [] arr = str.toCharArray();
		char res = ' ';
		for (char a : arr) {
		
			for (int i = 0 ; i < korArr.length ; i ++) {
			
				if (a == korArr[i]) {
					res = engArr[i];
					break;
					
				} else {
					res = a;
				}
				
			}
			
			result += res;
			
		}
		
		return result;
		
	}
}


