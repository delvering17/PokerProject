package login_p;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import net_p.Receiver;

public class FindIDPW_panel extends JPanel{
	
	Login_frame login_frame;
	Gen_textfiled name_find ;
	Gen_textfiled email_find;

	Gen_textfiled idpw_find ;


	
	public FindIDPW_panel(Login_frame login_frame) {
		
		setBounds(0,0,1200,800);
		setLayout(null);

		// 아이디 찾기
		add(new Gen_label("아이디 찾기",520,100,80,40));
	
		// 이름
		add(new Gen_label("이름",400,150,80,40));
		name_find = new Gen_textfiled(login_frame,"이름","Login_textfiled_id",500,150, 200,50);
 		// 이메일	
 		add(new Gen_label("이메일",400,200,80,40));
 		email_find = new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",500,200, 200,50);
 		
 		// 확인 버튼
 		add(new Gen_button_login(login_frame,"아이디 찾기","FindID_button",500,250, 140,50));
 		
 		
		// 비밀번호 찾기
		add(new Gen_label("비밀번호 찾기",500,400,80,40));
 		// 아이디
		add(new Gen_label("아이디",400,450,80,40));
		idpw_find = new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",500,450, 200,50);
 		// 창 나옴 ,  질문에 답 , 맞으면 다시 창열어서  알려줌 비번
		// 확인 버튼
		add(new Gen_button_login(login_frame,"비밀번호 찾기","FindPW_button",500,500, 140,50));
		
		
		// 나가기
		add(new Gen_button_login(login_frame,"취소","FindCancel_in",500,600, 100,50));

		
		add(name_find);
		add(email_find);
		add(idpw_find);
		
		
		
		
		
		
	}
}
