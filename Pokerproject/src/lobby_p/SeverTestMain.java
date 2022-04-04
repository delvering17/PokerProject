package lobby_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import lobby_i.UserData;
class MulServer {
	ObjectInputStream dis;
	ObjectOutputStream dos;
	UserData data;
	HashMap<String, ObjectOutputStream> userList = new HashMap<String, ObjectOutputStream>();
	public MulServer(Socket client) {
		new Reciver(client).start();
	}
	class Reciver extends Thread {
		public Reciver(Socket client) {
			try {
				dos = new ObjectOutputStream(client.getOutputStream());
				dis = new ObjectInputStream(client.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		@Override
		public void run() {
			try {
				while(dis!=null) {
					data = (UserData)dis.readObject();
					System.out.println(data.name + data.msg);
					userList.put(data.name, dos);
					dos.writeObject(data);
				}
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
public class SeverTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		System.out.println("나서버");
//		ServerSocket socket =null;
//		try {
//			socket = new ServerSocket(8888);
//			while(true) {
//				
//				Socket client = socket.accept();
//
//				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
//				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
//				
//				UserData data =  (UserData)ois.readObject();
//				System.out.println(data.msg);
//				oos.writeObject(data);
//				
//				oos.close();
//				ois.close();
//				
//				
//				
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}finally {
//			try {
////				socket.close();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		
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
