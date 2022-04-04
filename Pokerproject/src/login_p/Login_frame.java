package login_p;

import java.awt.Color;

import javax.swing.JFrame;


public class Login_frame extends JFrame {
	Login_panel login_panel ;
	Signin_panel signin_panel;
	FindIDPW_panel findIDPW_panel;
	public Login_frame() {
		
		setBounds(50,50,1200,800);
		setLayout(null);
	
		login_panel = new Login_panel(this);
		add(login_panel);
		
		
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
	}

	public static void main(String[] args) {
		
		new Login_frame();

	}

}