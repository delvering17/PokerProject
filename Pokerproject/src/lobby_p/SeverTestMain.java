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
class MulServer {

	HashMap<ObjectOutputStream, ProfileDTO> userList;
	
	public MulServer() {
		try {
			System.out.println("나 서버");
			userList = new HashMap<ObjectOutputStream,ProfileDTO>();
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
		String name;
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
					ProfileDTO data = (ProfileDTO)ois.readObject();
					userList.put(oos,data);
					System.out.println(data.nickname +" : "+ data.msg);
					sendToAll(data);
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				userList.remove(name);
			}
			
		}
	}
	void sendToAll(ProfileDTO data) {
		for (ObjectOutputStream dd : userList.keySet()) {
			try {
				
				if(data.roomNum==((ProfileDTO)userList.get(dd)).roomNum) {
					dd.writeObject(data);
					dd.flush();
					dd.reset();
				}
				
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
