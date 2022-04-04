package lobby_p;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

import lobby_i.UserData;

public class SeverTestMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("나서버");
		try {
			while(true) {
				ServerSocket socket = new ServerSocket(8888);
				
				Socket client = socket.accept();

				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				
				UserData data =  (UserData)ois.readObject();
				
				oos.writeObject(data);
				
				oos.close();
				ois.close();
				socket.close();
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
