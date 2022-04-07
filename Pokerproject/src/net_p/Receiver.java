package net_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import DB_p.ProfileDTO;
import game_p.Game_panel;
import lobby_p.Lobby;
import login_p.Login_frame;
import login_p.Login_panel;

public class Receiver extends Thread {
	
	
	public Game_panel game_panel;
	public Lobby lobby_panel;
	
	//public HashMap<String, NetExecute>map = new  HashMap<String, NetExecute>();
	
	public ObjectInputStream ois;
	public ObjectOutputStream oos;
	
	
	public void send(TCPData data) {
		try {
			oos.writeObject(data);
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public Receiver(Login_frame lframe, Socket client) {
		// TODO Auto-generated constructor stub
		//this.lframe = lframe;
		//map.put("lobby_panel",lobby_panel);
		//map.put("game_panel",game_panel);
		try {
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	
	public void run() {
		try {
			while(ois!=null) {
				
				TCPData data=(TCPData)ois.readObject();
				System.out.println(data.DataDestination);
				if(data.UserPos==-1) {
					lobby_panel.execute(data);
				}else if(data.UserPos>-1) {
					game_panel.execute(data);					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}