package lobby_p;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import lobby_i.RoomAction;
import lobby_i.UserData;

public class Lobby extends JPanel {
	JTextField jtf;
	JTextArea jta;
	String addr;
	Socket client;
	Lobby sss;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	UserData data;
	//들어올 유져 객체
	ArrayList<Object> userList = new ArrayList<Object>();
	LobbyMain mainJf;
	
	//방 체크할 리스트
	HashMap<InetAddress, Object> roomChk;
	ArrayList<RoomBtn> btnlist = new ArrayList<RoomBtn>();
	public Lobby(Socket client,LobbyMain mainJf) {
		
		this.mainJf = mainJf;
		sss= this;
		try {
			System.out.println(client);
			oos = new ObjectOutputStream(client.getOutputStream());
			ois = new ObjectInputStream(client.getInputStream());
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		
		setBounds(0, 0, 1200, 800);
		setBackground(Color.black);
		setLayout(null);
		
		Component roomAdd = new Component(10, 10, 800, 100);
		add(roomAdd);
		
		btnlist.add(new RoomBtn("방만들기","Making",590,35,100,60));		
		btnlist.add(new RoomBtn("바로입장","Enter",695,35,100,60));
		
		roomAdd.add(btnlist.get(0));
		roomAdd.add(btnlist.get(1));
		
		JScrollPane roomList = new JScrollPane();//스크롤팬 생성
		roomList.setBounds(10, 120, 800, 400);
		roomList.setBackground(Color.red);
		JPanel roomPanel = new JPanel();//스크롤팬에 붙일 패널 생성
		roomPanel.setBorder(new LineBorder(Color.white,10));
		roomPanel.setLayout(new GridLayout(3,3,10,10));
		
		for (int i = 0; i < 9; i++) {
			JLabel jl= new JLabel();
			jl.setBorder(new LineBorder(Color.black,2));
			jl.setOpaque(true);
			jl.setBackground(new Color(255,111,111));
			RoomBtn rBtn = new RoomBtn("만들기","Making","231.0.0."+(i+1),90,110,80,40);
			try {
				InetAddress ad = InetAddress.getByName(rBtn.addr);
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			jl.add(rBtn);
			roomPanel.add(jl);
		}
		
		Dimension size = new Dimension();
		size.setSize(780, 800);
		roomPanel.setPreferredSize(size);
		roomList.setViewportView(roomPanel);
		add(roomList);
		
		
		Component chatting = new Component(10, 530, 800, 220);
		add(chatting);
		jtf = new JTextField();
		jtf.setBounds(0, 200, 800, 20);
		jta = new JTextArea();
		JScrollPane js = new JScrollPane(jta);
		js.setBounds(0, 0, 800, 200);
		jta.setEditable(false);
		chatting.add(jtf,"Center");
		chatting.add(js,"South");
		
		
		jtf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					System.out.println(this);
					UserData test = new UserData("zzz","전현우","1000000000",jtf.getText());
					oos.writeObject(test);
					jtf.setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				} 
				
			}
		});
		
		Component userList = new Component(820,10 , 350, 500);
		add(userList);
		Component profile = new Component(820,520 , 350, 230);
		add(profile);
		
		Receiver ch = new Receiver();
		ch.start();
	}
	
	class RoomBtn extends JButton implements ActionListener{
		String cname;
		public RoomBtn(String name,String cname,int x,int y,int width , int height) {
			super(name);
			this.cname = cname;
			setBounds(x, y, width, height);
			addActionListener(this);
		}
		String addr;
		public RoomBtn(String name,String cname,String addr,int x,int y,int width , int height) {
			super(name);
			this.cname = cname;
			this.addr = addr;
			setBounds(x, y, width, height);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				System.out.println(cname);
				RoomAction ra = (RoomAction)Class.forName("lobby_i."+cname).newInstance();
				ra.room(roomChk,mainJf);
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
		}
	}
	
	class Component extends JPanel {
		public Component(int x,int y,int width , int height) {
			setBounds(x,y,width,height);
			setBackground(Color.blue);
			setLayout(null);
		}
	}
	

	class Receiver extends Thread {
		@Override
		public void run() {
			try {
				while(ois!=null) {
					data=(UserData)ois.readObject();
					jta.append(data.name + data.msg+"\n");
					System.out.println(data.name +" : "+ data.msg);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
