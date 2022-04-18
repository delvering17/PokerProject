package lobby_p;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import net_p.MsgData;
import net_p.TCPData;
import net_p.UserData;

class MulServer {

	HashMap<String,ObjectOutputStream> userList;

	public HashMap<Integer, HashMap<String, Integer>> test;

	
	synchronized void testMove(UserData td) {
		Integer no  = test.get(td.pre).get(td.nickName);
		
		test.get(td.pre).remove(td.nickName);
		
		
		if (no != null) {
		
			
			for(Map.Entry<String, Integer> me: test.get(td.pre).entrySet()) {
				
				if(me.getValue() > no) {
					
					test.get(td.pre).put(me.getKey(), me.getValue()-1);
				}
				
			}	
		}
		

		
		System.out.println(td.nickName+ "가 "+ td.pre+ "에서 " + td.pos+ "로 이동 ");
		
		no = test.get(td.pos).size();
		if (td.pos == -1) {
			
			test.get(td.pos).put(td.nickName, null);
		} else {
			test.get(td.pos).put(td.nickName, no);
		}
		

	}
	
	
	
	public MulServer() {
		try {

			ServerSocket server = new ServerSocket(8888);

			
			test = new HashMap<Integer, HashMap<String, Integer>>();
			for (int i = -1; i < 9; i++) {
				test.put(i,new HashMap<String, Integer >());
			}
			
			
			System.out.println("나 서버");
			userList = new HashMap<String,ObjectOutputStream>();
			Collections.synchronizedMap(userList);

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

					if(data.DataDestination.equals("testMove")) {

						UserData td = (UserData)data.oData;
						userList.put(td.nickName,oos);		
						
						testMove(td);
						data.oData = test; 
						sendToAll(data);
						
					} else {

						
						sendToAll(data);
					}
				}
			}catch (Exception e) {

			}finally {
				try {

//					oos.close();
//					ois.close();
				} catch (Exception e1) {

				}
			}
			
		}
	}
	synchronized  void sendToAll(TCPData data) {
		for (Map.Entry<String,ObjectOutputStream> dd: userList.entrySet()) {
			try {
				
				
				
				dd.getValue().writeObject(data);
				dd.getValue().flush();
				dd.getValue().reset();
				
				System.out.println("서버가 "+data.DataDestination+"을 목적으로 "+ dd.getKey()+"에게 보냄");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
//		for (ObjectOutputStream dd : userList.values()) {
//			try {
//				System.out.println("서버가 모두에게 보냄");
//				dd.writeObject(data);
//				dd.flush();
//				dd.reset();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}

}
public class SeverTestMain {

	public static void main(String[] args) {
		new MulServer();
	}

}
