package lobby_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;

import DB_p.ProfileDAO;
import DB_p.ProfileDTO;
import lobby_i.UserData;
import net_p.TCPData;
class MulServer {

	HashMap<ObjectOutputStream, TCPData> userList;
	
	public MulServer() {
		try {
			System.out.println("나 서버");
			userList = new HashMap<ObjectOutputStream,TCPData>();
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
		public Reciver(Socket client) {
			try {
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			
			try {
				while(ois!=null) {
					TCPData data = (TCPData)ois.readObject();
					userList.put(oos,data);
					System.out.println(data.name +" : "+ data.msg);
					sendToAll(data);
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
			}
			
		}
	}
	void sendToAll(TCPData data) {
		for (ObjectOutputStream dd : userList.keySet()) {
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
