package net_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import DB_p.ProfileDTO;
import login_p.Login_frame;
import login_p.Login_panel;

public class Receiver extends Thread {
	
	
	//public Login_frame lframe;
	//public Login_panel login_panel;
	
	public HashMap<String, NetExecute>map = new  HashMap<>();
	
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	
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
		map.put("lframe", lframe);
		try {
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	
	public void run() {
		try {
			while(ois!=null) {
				TCPData data=(TCPData)ois.readObject();
				
				map.get(data.kind).execute(data);
//				switch(data.pos) {
//				case :
//					lframe.execute(data);
//					break;
//				case "login_panel":
//					login_panel.execute(data);
//					break;
//				
//				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}