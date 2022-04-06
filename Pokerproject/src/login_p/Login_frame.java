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





public class Login_frame extends JFrame implements NetExecute{
	Login_panel login_panel;
	Signin_panel signin_panel;
	FindIDPW_panel findIDPW_panel;
	public Lobby lobby_panel;
	ProfileDTO userDTO;
	public ArrayList <Game_panel> game_panelarr= new ArrayList <Game_panel>();
	Login_frame login_frame;
	
	public JTextArea jta;
	
	
	
	public Login_frame(Socket client) {
		
		login_frame=this;
		setBounds(50,50,1200,800);
		setLayout(null);
	
		
		
		Receiver ch = new Receiver(this, client);
		ch.start();
		
		
		login_panel = new Login_panel(this, ch);
		//ch.login_panel = login_panel;
		ch.map.put("login_panel", login_panel);
		add(login_panel);
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	public static void main(String[] args) {
		
		try {
			Socket client = new Socket("192.168.20.39", 8888);
			new Login_frame(client);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void execute(TCPData data) {
		
		ProfileDTO dto = (ProfileDTO)data.rdata;
		
		System.out.println(dto.nickname);
		if(dto.msg!=null) {
			jta.append(dto.nickname + " : "+ dto.msg+"\n");
			System.out.println(dto.nickname +" : "+ dto.msg);
		}
		
	}

}