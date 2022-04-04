package login_p;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class Signin_panel extends JPanel {
	Login_frame login_frame;
	
	ArrayList<Gen_textfiled> signInfo ;
	ArrayList<Textfiled_password> pwInfo ;
	
	boolean doubleCheck_id = true;
	boolean doublecCheck_nickname = true;
	
	public Signin_panel(Login_frame login_frame) {
		this.login_frame = login_frame;
		setBounds(0,0,1200,800);
		setLayout(null);
		
		add(new Gen_label("회원가입",550,30,100,50));
		
		add(new Gen_label("아이디",400,100,80,40));
		add(new Gen_label("비밀번호",400,150,80,40));
		add(new Gen_label("비밀번호확인",400,200,80,40));
		add(new Gen_label("이름",400,250,80,40));
		add(new Gen_label("이메일",400,300,80,40));
		add(new Gen_label("닉네임",400,350,80,40));
		add(new Gen_label("성별",400,400,80,40));
		add(new Gen_label("비밀번호질문",400,450,80,40));
		add(new Gen_label("비밀번호답변",400,500,80,40));
		add(new Gen_label("자기소개",400,550,80,40));
		
		
		
		
		signInfo = new ArrayList<Gen_textfiled>();
		pwInfo = new ArrayList<Textfiled_password>();
		
		signInfo.add(new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",500,100, 200,50));
		pwInfo.add(new Textfiled_password(login_frame,"비밀번호",500,150, 200,50));
		pwInfo.add(new Textfiled_password(login_frame,"비밀번호확인",500,200, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"이름","Login_textfiled_id",500,250, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"이메일","Login_textfiled_id",500,300, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"닉네임","Login_textfiled_id",500,350, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"성별","Login_textfiled_id",500,400, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"비밀번호질문","Login_textfiled_id",500,450, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"비밀번호답변","Login_textfiled_id",500,500, 200,50));
		signInfo.add(new Gen_textfiled(login_frame,"자기소개","Login_textfiled_id",500,550, 200,50));
		
		
		for (Gen_textfiled gen_textfiled :signInfo) {
			add(gen_textfiled);
			
		}
		
		for (Textfiled_password textfiled_password : pwInfo) {
			add(textfiled_password);
		}
		
		add(new Gen_button_login(login_frame,"완료","SignInComplete_in",550,620, 100,50));
		add(new Gen_button_login(login_frame,"취소","SignInCancel_in",400,620, 100,50));
		
		add(new Gen_button_login(login_frame,"중복 확인","SignIn_ID_DoubleCheck",700,100, 100,50));
		add(new Gen_button_login(login_frame,"중복 확인","SignIn_Nickname_DoubleCheck",700,350, 100,50));
		
		
	}
	
}

