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
	HashSet<String> userName;
	HashMap<String,ObjectOutputStream> userList;
	HashMap<Integer,Integer[]> playData;
	public int[] easyStudy;
	public HashMap<Integer, HashMap<String, Integer>> test;
	public HashMap<Integer, Boolean> roomclose;
	
	void testMove(UserData td) {
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
		
		for (String un :test.get(-1).keySet()) {
			System.out.println("로비: "+un);
		}
		for (String un :test.get(0).keySet()) {
			System.out.println("0번방: "+un);
		}
	}
	
	
	public MulServer() {
		try {
			roomclose = new HashMap<Integer, Boolean>();
			for (int i = 0; i < 9; i++) {
				roomclose.put(i, false);
			}
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
			
			test = new HashMap<Integer, HashMap<String, Integer >>();
			for (int i = -1; i < 9; i++) {
				test.put(i,new HashMap<String, Integer >());
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
//					data.userName = userName;
					System.out.println("서버가 클라이언트에게 받음");
					if(data.DataDestination.equals("first")) {
//						data.playData = playData;
//						data.easyStudy = easyStudy;
//						data.test = test;
//						
//						sendTo(data);
					}else if(data.DataDestination.equals("testMove")) {
						System.out.println("서버가 뿌림?");
						UserData td = (UserData)data.oData;
						testMove(td);
						data.oData = test; 
						sendToAll(data);
						
					} else {
//						playData = data.playData;
//						easyStudy = data.easyStudy;
//						test = data.test;
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
				System.out.println("서버가 모두에게 보냄");
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
					userList.get(name).flush();
					userList.get(name).reset();
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
