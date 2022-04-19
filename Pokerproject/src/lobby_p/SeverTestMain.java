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
import net_p.RoomChk;
import net_p.TCPData;
import net_p.UserData;

class MulServer {

	HashMap<String,ObjectOutputStream> userList;

	public HashMap<Integer, HashMap<String, Integer>> test;
	public HashMap<Integer,Boolean> rk;
	
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
		

		
//		System.out.println(td.nickName+ "가 "+ td.pre+ "에서 " + td.pos+ "로 이동 ");
		
		no = test.get(td.pos).size();
		if (td.pos == -1) {
			
			test.get(td.pos).put(td.nickName, null);
		} else {
			test.get(td.pos).put(td.nickName, no);
		}
		
		
	}
	void roomchk(TCPData data) {
		RoomChk rc = (RoomChk)data.oData;
		rk.put(rc.roomNum, rc.bl);
	}
	
	
	public MulServer() {
		try {

			ServerSocket server = new ServerSocket(8888);

			
			test = new HashMap<Integer, HashMap<String, Integer>>();
			for (int i = -1; i < 9; i++) {
				test.put(i,new HashMap<String, Integer >());
			}
			rk = new HashMap<Integer, Boolean>();
			
			System.out.println("나 서버");
			userList = new HashMap<String,ObjectOutputStream>();
			Collections.synchronizedMap(userList);
			Collections.synchronizedMap(rk);
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
		String nname;
		boolean chk = true;
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
					if(chk) {
						nname = data.name;
						chk=false;
					}
//					if(data.DataDestination.equals("testMove")) {
//
//						UserData td = (UserData)data.oData;
//						userList.put(td.nickName,oos);		
//						
//						testMove(td);
//						data.oData = test; 
//						sendToAll(data);
//						
//					} else {
//
//						
//						sendToAll(data);
//					}
					
					switch (data.DataDestination) {
					case "testMove":
						UserData td = (UserData)data.oData;
						userList.put(td.nickName,oos);		
						
						testMove(td);
						data.oData = test; 
						sendToAll(data);
						
						if(td.pos.equals(-1)) {
							data.DataDestination = "rk";
							data.oData = rk;
							sendTo(data);
						}
						break;
					case "RoomChk":
						roomchk(data);
						sendToAll(data);
						break;
					case "betting_bbing":
						sendToGame(data);
						break;
					case "betting_ddadang":
						sendToGame(data);
						break;
					case "betting_half":
						sendToGame(data);
						break;
					case "betting_quarter":
						sendToGame(data);
						break;
					case "betting_max":
						sendToGame(data);
						break;
					case "betting_die":
						sendToGame(data);
						break;
					case "betting_call":
						sendToGame(data);
						break;
					case "카드 나와라":
						sendToGame(data);
						break;
					default:
						sendToAll(data);
						break;
					}
				}
			}catch (Exception e) {

			}finally {
				try {
					TCPData td = new TCPData();
					userList.remove(nname);
					test.get(-1).remove(nname);
					td.UserPos = -1;
					td.oData = test;
					td.DataDestination = "testMove";
					sendToAll(td);
					oos.close();
					ois.close();
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
				
//				System.out.println("서버가 "+data.DataDestination+"을 목적으로 "+ dd.getKey()+"에게 보냄");
				
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
	synchronized  void sendToGame(TCPData data) {
		for (String gm : test.get(data.UserPos).keySet()) {
			try {
				userList.get(gm).writeObject(data);
				userList.get(gm).flush();
				userList.get(gm).reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	synchronized  void sendTo(TCPData data) {
		try {
			userList.get(data.name).writeObject(data);
			userList.get(data.name).flush();
			userList.get(data.name).reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
public class SeverTestMain {

	public static void main(String[] args) {
		new MulServer();
	}

}
