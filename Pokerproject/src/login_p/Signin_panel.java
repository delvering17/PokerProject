package login_p;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Signin_panel extends JPanel {
	Login_frame login_frame;
	
	ArrayList<Gen_textfiled> signInfo ;
	ArrayList<Textfiled_password> pwInfo ;
	Vector<String> genderList;
	boolean doubleCheck_id = true;
	boolean doublecCheck_nickname = true;
	String idCon = "";
	String nickNameCon = "";
	JComboBox<String> genderInfo;
	JComboBox<String> emailInfo;
	
	Gen_button_login idDouble;
	Gen_button_login NickDouble;
	public Signin_panel(Login_frame login_frame) {
		
		this.login_frame = login_frame;
		setBounds(0,0,1200,800);
		setLayout(null);
		
		add(new Gen_label("회원가입",550,30,100,50));
		
		add(new Gen_label("* 아이디",400,100,80,40));
		add(new Gen_label("* 비밀번호",400,150,80,40));
		add(new Gen_label("* 비밀번호확인",400,200,80,40));
		add(new Gen_label("* 이름",400,250,80,40));
		add(new Gen_label("* 이메일",400,300,80,40));
		add(new Gen_label("* 닉네임",400,350,80,40));
		add(new Gen_label("  성별",400,400,80,40));
		add(new Gen_label("* 비밀번호질문",400,450,80,40));
		add(new Gen_label("* 비밀번호답변",400,500,80,40));
		add(new Gen_label("  자기소개",400,550,80,40));
		
		add(new Gen_label("  자기소개",400,550,80,40));
		add(new Gen_label("   '*'이 붙은 항목은 필수 항목입니다",500,595,250,40));
		
		add(new Gen_label("아이디: 영문 시작, 영문 또는 숫자 조합, 5~12자리",815,100,250,40));
		add(new Gen_label("닉네임: 한글, 2~8자리",815,350,250,40));
		add(new Gen_label("비밀번호: 영문+숫자+특수문자 조합, 6~16자리",815,150,250,40));
		add(new Gen_label("비밀번호 답변은 비밀번호 찾기에 사용, 신중히 기재 요망",815,500,350,40));
		
		add(new Gen_label(" @ ",700,300,20,45));
		signInfo = new ArrayList<Gen_textfiled>();
		pwInfo = new ArrayList<Textfiled_password>();
		
		signInfo.add(new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",500,100, 200,45));
		pwInfo.add(new Textfiled_password(login_frame,"비밀번호",500,150, 200,45));
		pwInfo.add(new Textfiled_password(login_frame,"비밀번호확인",500,200, 200,45));
		signInfo.add(new Gen_textfiled(login_frame,"이름","Login_textfiled_id",500,250, 200,45));
		signInfo.add(new Gen_textfiled(login_frame,"이메일","Login_textfiled_id",500,300, 200,45));
		signInfo.add(new Gen_textfiled(login_frame,"닉네임","Login_textfiled_id",500,350, 200,45));
		signInfo.add(new Gen_textfiled(login_frame,"성별","Login_textfiled_id",0,0, 0,0));
		signInfo.add(new Gen_textfiled(login_frame,"비밀번호질문","Login_textfiled_id",500,450, 200,45));
		signInfo.add(new Gen_textfiled(login_frame,"비밀번호답변","Login_textfiled_id",500,500, 200,45));
		signInfo.add(new Gen_textfiled(login_frame,"자기소개","Login_textfiled_id",500,550, 200,45));
		
		emailInfo = new JComboBox<String>();
		emailInfo.setModel(new DefaultComboBoxModel<String>(new String[] { "naver.com", "hanmail.net" ,"daum.net", "gmail.com", "hotmail.com"}));
		emailInfo.setBounds(725,300, 160,45);
//		genderInfo.setSelectedItem("무관");	
		emailInfo.setBackground(Color.white);
		add(emailInfo);
		
		genderInfo = new JComboBox<String>();
		genderInfo.setModel(new DefaultComboBoxModel<String>(new String[] { "비공개","남", "여", "무관" }));
		genderInfo.setBounds(500,400, 200,45);
//		genderInfo.setSelectedItem("무관");	
		genderInfo.setBackground(Color.white);
		add(genderInfo);
//		genderInfo.getSelectedItem().toString();

		
		for (Gen_textfiled gen_textfiled :signInfo) {
			add(gen_textfiled);
			
		}
		
		for (Textfiled_password textfiled_password : pwInfo) {
			add(textfiled_password);
		}
		
		add(new Gen_button_login(login_frame,"완료","SignInComplete_in",620,635, 80,45));
		add(new Gen_button_login(login_frame,"취소","SignInCancel_in",500,635, 80,45));
		
		idDouble = new Gen_button_login(login_frame,"중복 확인","SignIn_ID_DoubleCheck",710,100, 100,45);
		add(idDouble);
		NickDouble = new Gen_button_login(login_frame,"중복 확인","SignIn_Nickname_DoubleCheck",710,350, 100,45);
		add(NickDouble);
		
		
	}
	
}

