package lobby_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;

import net_p.TCPData;
class MulServer {

	HashMap<String,ObjectOutputStream> userList;
	HashMap<Integer,Integer[]> playData;
	public int[] easyStudy;
	public MulServer() {
		playData = new HashMap<Integer, Integer[]>();
		easyStudy = new int[9];
		for (int j = 0; j < 9; j++) {
			playData.put(j,new Integer[5]);
			for (int i = 0; i < playData.get(j).length; i++) {
				playData.get(j)[i] = -1;
			}
		}
		try {
			System.out.println("나 서버");
			userList = new HashMap<String,ObjectOutputStream>();
			Collections.synchronizedMap(userList);
			ServerSocket server = new ServerSocket(8888);
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
					userList.put(data.name,oos);
					System.out.println(data.name +" : "+ data.msg);
					if(data.DataDestination.equals("first")) {
						for (String name : userList.keySet()) {
							if(name==data.name) {
								data.playData = playData;
								data.easyStudy = easyStudy;
								oos.writeObject(data);
								oos.flush();
								oos.reset();
							}
						}
					}else {
						playData = data.playData;
						easyStudy = data.easyStudy;
						sendToAll(data);
					}
				}
			}catch (Exception e) {
//				e.printStackTrace();
			}finally {
				try {
					userList.remove(data.name);
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
}
public class SeverTestMain {

	public static void main(String[] args) {
		new MulServer();
	}

}
