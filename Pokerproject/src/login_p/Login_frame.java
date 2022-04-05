package login_p;

import java.util.ArrayList;

import javax.swing.JFrame;

import lobby_p.Lobby;
import game_p.Game_panel;

public class Login_frame extends JFrame {
	Login_panel login_panel;
	Signin_panel signin_panel;
	FindIDPW_panel findIDPW_panel;
	public Lobby lobby_panel;
	
	public ArrayList <Game_panel> game_panelarr= new ArrayList <Game_panel>();
	
	
	Login_frame login_frame;
	public Login_frame() {
		login_frame=this;
		RepaintT test = new RepaintT();
		test.start();
		setBounds(50,50,1200,800);
		setLayout(null);
	
		login_panel = new Login_panel(this);
		add(login_panel);
		
		
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
	}
	class RepaintT extends Thread {
		@Override
		public void run() {
			login_frame.repaint();
		}
	}
	
	public static void main(String[] args) {
		
		new Login_frame();

	}

}