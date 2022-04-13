package login_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import DB_p.ProfileDTO;
import game_p.Game_panel;
import lobby_p.Lobby;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.TCPData;

public class Login_frame extends JFrame{
	Login_panel login_panel;
	Signin_panel signin_panel;
	FindIDPW_panel findIDPW_panel;
	public Lobby lobby_panel;
	ProfileDTO userDTO;
	public ArrayList <Game_panel> game_panelarr= new ArrayList<Game_panel>();
	Login_frame login_frame;
	public JTextArea jta;
	
	public Login_frame() {
		
		login_frame=this;
		setBounds(50,50,1200,800);
		setLayout(null);
		
		login_panel = new Login_panel(this);
		add(login_panel);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		

	}
	
	public static void main(String[] args) {
		
		new Login_frame();

	}

}