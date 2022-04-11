package lobby_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import net_p.TCPData;
class MulServer {
	HashSet<String> userName;
	HashMap<String,ObjectOutputStream> userList;
	HashMap<Integer,Integer[]> playData;
	public int[] easyStudy;
	public HashMap<Integer, HashMap<Integer, String>> test;
	public MulServer() {
		try {
			ServerSocket server = new ServerSocket(8888);
			userName = new HashSet<String>();
			playData = new HashMap<Integer, Integer[]>();
			easyStudy = new int[9];
			for (int j = 0; j < 9; j++) {
				playData.put(j,new Integer[5]);
				for (int i = 0; i < playData.get(j).length; i++) {
					playData.get(j)[i] = -1;
				}
			}
			
			test = new HashMap<Integer, HashMap<Integer, String>>();
			for (int i = 0; i < 9; i++) {
				test.put(i,new HashMap<Integer, String>());
			}
			
			
			System.out.println("나 서버");
			userList = new HashMap<String,ObjectOutputStream>();
			Collections.synchronizedMap(userList);
			Collections.synchronizedMap(playData);
			Collections.synchronizedSet(userName);
			Collections.synchronizedMap(test);
			while(true) {
				Socket client = server.accept();
				new Reciver(client).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	class Reciver extends Thread {
		ObjectInputStream ois;
		ObjectOutputStream oos;
		Socket client;
		public Reciver(Socket client) {
			try {
				this.client = client;
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		TCPData data;
		@Override
		public void run() {
			try {
				while(ois!=null) {
					data = (TCPData)ois.readObject();
					userName.add(data.name);
					userList.put(data.name,oos);
					data.userName = userName;
					System.out.println(data.name +" : "+ data.msg);
					if(data.DataDestination.equals("first")) {
						data.playData = playData;
						data.easyStudy = easyStudy;
						data.test = test;
						sendTo(data);
					}else {
						playData = data.playData;
						easyStudy = data.easyStudy;
						test = data.test;
						sendToAll(data);
					}
				}
			}catch (Exception e) {
//				e.printStackTrace();
			}finally {
				try {
//					userList.remove(data.name);
//					userName.remove(data.name);
//					data.userName = userName;
//					sendToAll(data);
//					data.DataDestination = "Chatting";
//					data.msg = "[퇴장]";
//					sendToAll(data);
//					oos.close();
//					ois.close();
				} catch (Exception e1) {

				}
			}
			
		}
	}
	void sendToAll(TCPData data) {
		for (ObjectOutputStream dd : userList.values()) {
			try {
				dd.writeObject(data);
				dd.flush();
				dd.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	void sendTo(TCPData data) {
		for (String name : userList.keySet()) {
			if(name==data.name) {
				try {
					userList.get(name).writeObject(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
public class SeverTestMain {

	public static void main(String[] args) {
		new MulServer();
	}

}
