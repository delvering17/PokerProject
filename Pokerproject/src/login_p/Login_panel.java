package login_p;


import java.awt.Color;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;
import javax.swing.JLabel;
import DB_p.ProfileDTO;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.TCPData;
import javax.swing.ImageIcon;
// 


public class Login_panel extends JPanel{
	Login_frame login_frame;
	Gen_textfiled id_login ;
	Login_Textfiled_password pw_login;
	ImageIcon img;
	Gen_button_login login_bt;
	Gen_button_login findidpass_bt;
	Gen_button_login signin_bt;
	public Login_panel(Login_frame login_frame) {
		
		setBounds(0,0,1200,800);
		setLayout(null);
		
		
		add(new Gen_label("아이디",720,400,80,40));
		add(new Gen_label("비밀번호",720,450,80,40));
		
		id_login = new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",770,400, 200,50);
		pw_login = new Login_Textfiled_password(login_frame,"비밀번호",770,450, 200,50); 
		add(id_login);
		add(pw_login);
		
		img = new ImageIcon("img/login/lo.png");
		login_bt = new Gen_button_login(login_frame,"","LogIn_in",970,400, 101,101);
		login_bt.setIcon(img);
		add(login_bt);
		
		img = new ImageIcon("img/login/idpw.png");
		findidpass_bt = new Gen_button_login(login_frame,"","FindInfo_in",770,505, 200,52);
		findidpass_bt.setIcon(img);
		add(findidpass_bt);

		img = new ImageIcon("img/login/signup.png");
		signin_bt = new Gen_button_login(login_frame,"","SignIn_in",770,555,200,52);
		signin_bt.setIcon(img);
		add(signin_bt);

		
		img = new ImageIcon("img/gamepanel/img2.png");
		JLabel lb = new JLabel(img);
		lb.setBounds(0,0,1200,800);
		add(lb);
	
		//ch.send(data);
	//통신 금; 
	//게임 0% 일요일 
		
	}
	
}


