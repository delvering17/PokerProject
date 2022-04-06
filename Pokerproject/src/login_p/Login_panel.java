package login_p;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;

import DB_p.ProfileDTO;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.TCPData;

// 


public class Login_panel extends JPanel  implements NetExecute{
	Login_frame login_frame;
	Gen_textfiled id_login ;
	Textfiled_password pw_login;
	Receiver ch ;
	public Login_panel(Login_frame login_frame, Receiver ch ) {
		this.ch = ch;
		
		setBounds(0,0,1200,800);
		setLayout(null);
		
		
		add(new Gen_label("아이디",350,400,80,40));
		add(new Gen_label("비밀번호",350,450,80,40));
		
		id_login = new Gen_textfiled(login_frame,"아이디","Login_textfiled_id",400,400, 200,50);
		pw_login = new Textfiled_password(login_frame,"비밀번호",400,450, 200,50); 
		add(id_login);
		add(pw_login);
		
		//add(new Gen_button_login(login_frame,"로그인","LogIn_in",600,400, 100,100,ch));
		//add(new Gen_button_login(login_frame,"아이디/비밀번호찾기","FindInfo_in",400,500, 200,50,ch));
		//add(new Gen_button_login(login_frame,"회원가입","SignIn_in",400,550, 200,50,ch));
		
		//ch.send(data);
	//통신 금; 
	//게임 0% 일요일 
		
	}
	@Override
	public void execute(TCPData data) {
		
		ProfileDTO dto = (ProfileDTO)data.rdata;
		
		System.out.println(dto.nickname);
		
		if(dto.msg!=null) {
			//jta.append(dto.nickname + " : "+ dto.msg+"\n");
			System.out.println(dto.nickname +" : "+ dto.msg);
		}	
	}
	
	
	
}


