package login_p;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Gen_textfiled extends JTextField implements ActionListener {
	Login_frame login_frame;
	String function;
	public Gen_textfiled(Login_frame login_frame,String name, String function, int dis_x, int dis_y, int size_x, int size_y) {
		setName(name);
		this.login_frame = login_frame;
		setBounds(dis_x, dis_y, size_x, size_y);
		this.function = function;
		
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			Inter_textfield_signin inter_button_login = (Inter_textfield_signin)Class.forName("login_p."+function).newInstance();
			inter_button_login.go(login_frame);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
	}
}
interface Inter_textfield_signin{
	
	public void go(Login_frame login_frame);
}


class Login_textfiled_id implements Inter_textfield_signin  {

	@Override
	public void go(Login_frame login_frame) {
		
		
		
	}


}
class Login_Textfiled_password extends JPasswordField implements ActionListener  {
	
	
	Login_frame login_frame;
	public Login_Textfiled_password (Login_frame login_frame,String name , int dis_x, int dis_y, int size_x, int size_y) {
	
		
		this.login_frame = login_frame;
		setBounds(dis_x, dis_y, size_x, size_y);
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		new LogIn_in().go(login_frame);
		
	}

}

class Textfiled_password extends JPasswordField implements ActionListener  {
	
	
	Login_frame login_frame;
	public Textfiled_password (Login_frame login_frame,String name , int dis_x, int dis_y, int size_x, int size_y) {
	
		
		this.login_frame = login_frame;
		setBounds(dis_x, dis_y, size_x, size_y);
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}