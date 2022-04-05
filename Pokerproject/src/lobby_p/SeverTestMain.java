package lobby_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import DB_p.ProfileDAO;
import DB_p.ProfileDTO;
import lobby_i.UserData;
class MulServer {
	ObjectInputStream dis;
	ObjectOutputStream dos;
	String name;
	HashMap<String, ObjectOutputStream> userList;
	public MulServer(Socket client) {
		new Reciver(client).start();
	}
	class Reciver extends Thread {
		public Reciver(Socket client) {
			userList = new HashMap<String, ObjectOutputStream>();
			try {
				dos = new ObjectOutputStream(client.getOutputStream());
				dis = new ObjectInputStream(client.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			
				while(dis!=null) {
					try {
					ProfileDTO data = (ProfileDTO)dis.readObject();
					name = data.nickname;
					userList.put(data.nickname, dos);
					System.out.println(data.nickname +" : "+ data.msg);
					sendToAll(data);
					}catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						userList.remove(name);
					}
				}
			
		}
	}
	void sendToAll(ProfileDTO data) {
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

		try {
			System.out.println("나 서버");
			ServerSocket server = new ServerSocket(8888);
			while(true) {
				Socket client = server.accept();
				new MulServer(client);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
