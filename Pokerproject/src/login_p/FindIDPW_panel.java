package login_p;

import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net_p.Receiver;

public class FindIDPW_panel extends JPanel{
	
	Login_frame login_frame;
	Gen_textfiled name_find ;
	Gen_textfiled email_find;

	Gen_textfiled idpw_find ;

	ImageIcon img;
	Gen_button_login confirm_bt;
	Gen_button_login ppconfirm_bt;
	Gen_button_login out_bt;
	public FindIDPW_panel(Login_frame login_frame) {
		
		setBounds(0,0,1200,800);
		setBackground(new Color(247,247,247));
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
		img = new ImageIcon("img/login/idk.png");
		confirm_bt = new Gen_button_login(login_frame,"","FindID_button",500,255, 100,50);
		confirm_bt.setIcon(img);
		add(confirm_bt);

 		
 		
		// 비밀번호 찾기
		add(new Gen_label("비밀번호 찾기",500,400,80,40));
 		// 아이디
		add(new Gen_label("아이디",400,450,80,40));
		idpw_find = new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",500,450, 200,50);
 		// 창 나옴 ,  질문에 답 , 맞으면 다시 창열어서  알려줌 비번
		// 확인 버튼
		img = new ImageIcon("img/login/pwh.png");
		ppconfirm_bt = new Gen_button_login(login_frame,"","FindPW_button",500,505, 100,50);
		ppconfirm_bt.setIcon(img);
		add(ppconfirm_bt);

		
		
		// 나가기
		img = new ImageIcon("img/login/close.png");
		out_bt = new Gen_button_login(login_frame,"","FindCancel_in",500,600, 100,50);
		out_bt.setIcon(img);
		add(out_bt);


		
		
		
		add(name_find);
		add(email_find);
		add(idpw_find);
		
//		ImageIcon img = new ImageIcon("img/gamepanel/test3.jpg");
//		JLabel lb = new JLabel(img);
//		lb.setBounds(0,0,1200,800);
//		add(lb);
		
		
		
		
	}
}
