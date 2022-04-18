package login_p;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DB_p.ProfileDAO;
import DB_p.ProfileDTO;
import DB_p.SignDB;
import lobby_p.Lobby;
import net_p.MyData;
import net_p.Receiver;
import net_p.TCPData;
import net_p.UserData;




class Gen_button_login extends JButton implements ActionListener {
	Login_frame login_frame;
	String function;
	public Gen_button_login(Login_frame login_frame,String name, 
			String function, int dis_x, int dis_y, int size_x, int size_y) {
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
		
		boolean res = false;
		boolean respw = false;
		res = new SignDB().DBmemCheck("id = '"+id_login + "';") ;
		
		if (new GongbackCon().con(id_login) || new GongbackCon().con(pw_login) ) {
			new noticeWindow("빈 칸을 채워주세요", "오류", JOptionPane.ERROR_MESSAGE);
			
		} else {
			if (res) {
				
				respw = new SignDB().login_check("id = '"+
						id_login + "';").equals(pw_login);
				if (respw) {
					// 성공 시 대기실 화면 전환
					System.out.println("로그인 성공");
					//// 갓찬욱 개잘생김
					
					int memberNum = new SignDB().num_get("id = '"+id_login + "';");
					
					
					ProfileDTO datathis = new SignDB().num_profileRead(" profilenum = " +memberNum+ ";");
					try {
						Socket client = new Socket("192.168.20.34", 8888);
						Receiver ch = new Receiver(login_frame, client);
						TCPData tcpdata = new TCPData();
						ch.start();
						MyData myData = new MyData(datathis);
						login_frame.remove(login_frame.login_panel);
						//클라이언트를 서버에 보내기 시작
					
						
						
						Lobby lobby_panel = new Lobby(login_frame,ch,myData);
						login_frame.add(lobby_panel) ;
						login_frame.userDTO = datathis;
						login_frame.lobby_panel = lobby_panel;
						login_frame.repaint();
						tcpdata.name = myData.nickName;
						tcpdata.DataDestination = "testMove";
						tcpdata.oData = new UserData(-1,-1,datathis.nickname,null);
						ch.send(tcpdata);
					}catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					new noticeWindow("로그인 실패", "오류", JOptionPane.ERROR_MESSAGE);
				}
				
				
			} else {
				new noticeWindow("정보가 맞지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
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
		gender = login_frame.signin_panel.genderInfo.getSelectedItem().toString();
		introduce = signInfo.get(7).getText().trim();
		totalGame = 0;
		win = 0;
		lose = 0;
		money = 1000000; 
		String a = login_frame.signin_panel.nickNameCon;
		String b = nickname;
		String c = login_frame.signin_panel.idCon;
		String d = id;
		boolean resA = false;
		boolean resB = false;
		boolean resGongback = false;
		String Gong = "";
		for (int i = 0 ; i < 7 ; i++) {
			if (new GongbackCon().sicon(signInfo.get(i).getText()) && i != 5) {
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
			new noticeWindow("빈 칸을 채워주세요", "오류", JOptionPane.ERROR_MESSAGE);
		
		} else if (resGongback) {

			new noticeWindow(Gong +"의 공백을 제외하고 입력해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new CharLimit().email(email)) {
			
			new noticeWindow("이메일 형식에 맞지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new CharLimit().password(password)) {
			
			new noticeWindow("비밀번호는 6~16자리이며 영문+숫자+특수문자 조합이어야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
			
		} else if (!password.equals(passwordCon)) {
		
			new noticeWindow("비밀번호와 확인이 일치하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (login_frame.signin_panel.doubleCheck_id) {
			
			new noticeWindow("아이디 중복 체크를 해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (login_frame.signin_panel.doublecCheck_nickname) {
		
			new noticeWindow("닉네임 중복 체크를 해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (!c.equals(d)) {
		
			new noticeWindow("아이디 중복 체크를 다시 해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		}  else if (!a.equals(b)) {
			
			new noticeWindow("닉네임 중복 체크를 다시 해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else {
			new noticeWindow("회원가입 완료", "완료", JOptionPane.PLAIN_MESSAGE);
		
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
			
			new noticeWindow("아이디를 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new CharLimit().id(str)) {	
			
			new noticeWindow("아이디는 영문으로 시작하는 영문+숫자 조합의 5~12자리여야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new GongbackCon().sicon(str)) {
			new noticeWindow("아이디의 공백을 제거해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
		} else {
			boolean res = true;
			
			res = new SignDB().DBmemCheck("id = '"+
			str + "';");
			
			if (res) {
				
				new noticeWindow("중복된 아이디", "오류", JOptionPane.ERROR_MESSAGE);
			} else {
				new noticeWindow("아이디 사용 가능", "사용가능", JOptionPane.INFORMATION_MESSAGE);
				login_frame.signin_panel.doubleCheck_id = false;
				login_frame.signin_panel.idCon = str;
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
			
			new noticeWindow("닉네임을 입력해주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new CharLimit().nickname(str)) {
			new noticeWindow("닉네임은 2~8자리여야 합니다.", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new GongbackCon().sicon(str)) {
			new noticeWindow("닉네임의 공백을 제거해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
		} else {
			boolean res = true;
			res = new SignDB().DBproCheck("nickname = '"+
			str + "';");
			
			if (res) {

				new noticeWindow("중복된 닉네임", "오류", JOptionPane.ERROR_MESSAGE);
				
			} else {
				new noticeWindow("닉네임 사용 가능", "사용 가능", JOptionPane.INFORMATION_MESSAGE);
				login_frame.signin_panel.doublecCheck_nickname = false;
				login_frame.signin_panel.nickNameCon = str;
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
		
			new noticeWindow("빈칸을 채워주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new GongbackCon().sicon(name_find) ||new GongbackCon().sicon(email_find) ) {
			new noticeWindow("공백을 제거해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
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
				
					new noticeWindow("해당되는 정보가 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
				}
				
				
			} else {
				
				new noticeWindow("해당되는 정보가 없습니다", "오류", JOptionPane.ERROR_MESSAGE);
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
			
			new noticeWindow("빈칸을 채워주세요", "오류", JOptionPane.ERROR_MESSAGE);
		} else if (new GongbackCon().sicon(idpw_find) ) {
			new noticeWindow("공백을 제거해주세요.", "오류", JOptionPane.ERROR_MESSAGE);
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
				
					new noticeWindow("답이 맞지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
		

				}

		
			} else {
			
				new noticeWindow("정보가 맞지 않습니다", "오류", JOptionPane.ERROR_MESSAGE);
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
		String eng = "abcdefghijklmnopqrstuvwxyzQWERTOP";
		
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

class noticeWindow {
	
	public noticeWindow(String message, String title, int informationMessage) {
		JOptionPane.showMessageDialog(null, message, title, informationMessage);
	}
	/*
	 - ERROR_MESSAGE
     - INFORMATION_MESSAGE
     - QUESTION_MESAGE
     - WARNING_MESSAGE
     - PLAIN_MESSAGE 
	 */
}

class emailcon {
	
	public emailcon() {
		
		
		
	}
	
}

class CharLimit {

	public CharLimit() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean id(String id) { // id 5~12 영어로 시
		boolean res = true;
		
		
		String id_regex = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
		
		if (Pattern.matches(id_regex, id)) {
			res = false;
		}
		
		return res;
			
	}
	
	public boolean email(String email) {
		boolean res = true;
		
		String email_regex = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
		
		if (Pattern.matches(email_regex, email)) {
			res = false;
		}
		
		return res;
		
	}
	
	public boolean nickname(String nickname) {
		boolean res = true;
		String nick = ""+nickname;
		char[] concon = nick.toCharArray();
		if (concon.length > 1 && concon.length < 9) {
			res = false;
		} 
		return res;
	}
	
	public boolean password(String password) { // 6~16
		boolean res = true;

		String pass = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
		
		if ((Pattern.matches(pass, password))) {
			res = false;
		}
		
		return res;
	}
}







