package lobby_p;

import java.awt.Graphics;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;



public class LobbyMain extends JFrame{
	LobbyMain test;
	public LobbyMain(Socket client) {
		test=this;
		setLayout(null);
		setBounds(200, 100, 1200, 800);
		add(new Lobby(client,this));
		
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public static void main(String[] args) {
		try {
			Socket client = new Socket("192.168.20.39", 8888);
			System.out.println(client.getLocalAddress());
			new LobbyMain(client);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}