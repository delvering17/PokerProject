package login_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.JFrame;

import DB_p.ProfileDTO;
import game_p.Game_panel;
import lobby_p.Lobby;

public class Login_frame extends JFrame {
	Login_panel login_panel;
	Signin_panel signin_panel;
	FindIDPW_panel findIDPW_panel;
	public Lobby lobby_panel;
	ProfileDTO userDTO;
	public ArrayList <Game_panel> game_panelarr= new ArrayList <Game_panel>();
	Login_frame login_frame;
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	public Login_frame(Socket client) {
		try {
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		login_frame=this;
		setBounds(50,50,1200,800);
		setLayout(null);
	
		login_panel = new Login_panel(this,oos,ois);
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
		
		try {
			Socket client = new Socket("192.168.20.39", 8888);
			new Login_frame(client);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}