package lobby_p;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import lobby_i.RoomAction;
import login_p.Login_frame;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.TCPData;

public class Lobby extends JPanel implements NetExecute {
	JTextField jtf;
	JTextArea jta;
	Receiver ch;
	
	//들어올 유져 객체
//	ArrayList<Object> userList = new ArrayList<Object>();
	Login_frame mainJf;
	TCPData tcpdata;
	//방 체크할 리스트
	HashMap<InetAddress, Object> roomChk;
	ArrayList<RoomBtn> btnlist = new ArrayList<RoomBtn>();
	public Lobby(Login_frame mainJf,TCPData tcpdata) {
		
		Socket client;
		try {
			//클라이언트를 서버에 보내기 시작
			client = new Socket("192.168.20.39", 8888);
			ch = new Receiver(mainJf, client);
			ch.lobby_panel=this;
			tcpdata.panelChk = "Lobby";
			ch.start();
		}catch (Exception e) {
			e.printStackTrace();
		}

		this.mainJf = mainJf;
		this.tcpdata = tcpdata;
		try {
		
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		setBounds(0, 0, 1200, 800);
		setBackground(Color.black);
		setLayout(null);
		
		Component roomAdd = new Component(10, 10, 800, 100);
		add(roomAdd);
		
		roomAdd.add(new RoomBtn("방만들기","Making",590,35,100,60,this,tcpdata));
		roomAdd.add(new RoomBtn("바로입장","Enter",0,695,35,100,60,this,tcpdata));
		
		JScrollPane roomList = new JScrollPane();//스크롤팬 생성
		roomList.setBounds(10, 120, 800, 400);
		JPanel roomPanel = new JPanel();//스크롤팬에 붙일 패널 생성
		roomPanel.setBorder(new LineBorder(Color.white,10));
		roomPanel.setLayout(new GridLayout(3,3,10,10));
		
		for (int i = 0; i < 9; i++) {
			JLabel jl= new JLabel();
			jl.setBorder(new LineBorder(Color.black,2));
			jl.setOpaque(true);
			jl.setBackground(new Color(255,111,111));
			RoomBtn rBtn = new RoomBtn("만들기","Making",i,90,110,80,40,this,tcpdata);
			btnlist.add(rBtn);
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
		
		//ch 리시버 연결 필요
		jtf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tcpdata.msg = jtf.getText();
				tcpdata.DataDestination = "Chatting";
				ch.send(tcpdata);
				jta.setCaretPosition(jta.getDocument().getLength());
				jtf.setText("");
			}
		});
		
		Component userList = new Component(820,520 , 350, 230);
		add(userList);
		tcpdata.msg = "[입장]";
		tcpdata.DataDestination = "Chatting";
		ch.send(tcpdata);
		//아직 모름 일딴 TCP 데이터 정지
//		UserProfile_panel profilePanel = new UserProfile_panel(tcpdata);
//		add(profilePanel);
		repaint();
	}
	
	class RoomBtn extends JButton implements ActionListener{
		String cname;
		String name;
		Lobby lobby;
		public RoomBtn(String name,String cname,int x,int y,int width , int height,Lobby lobby,TCPData tcptcpdata) {
			super(name);
			this.cname = cname;
			this.name = name;
			this.lobby = lobby;
			setBounds(x, y, width, height);
			addActionListener(this);
		}
		Integer addr;
		public RoomBtn(String name,String cname,int addr,int x,int y,int width , int height,Lobby lobby,TCPData tcptcpdata) {
			super(name);
			this.cname = cname;
			this.name = name;
			this.addr = addr;
			this.lobby = lobby;
			setBounds(x, y, width, height);
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				RoomAction ra = (RoomAction)Class.forName("lobby_i."+cname).newInstance();
				ra.room(mainJf,ch,lobby,tcpdata,addr);
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

	@Override
	public void execute(TCPData data) {
		
		
		switch (data.DataDestination) {
		case "Chatting":
			if(data.UserPos==this.tcpdata.UserPos) {
				jta.append(data.name + " : "+ data.msg+"\n");
			}
			break;

		case "GameData":
			
			break;
		case "RoomChk":
			this.tcpdata.easyStudy = data.easyStudy;
			System.out.println(this.tcpdata.easyStudy[0]);
			for (RoomBtn roomBtn : btnlist) {
				
				if(this.tcpdata.easyStudy[roomBtn.addr]>0) {
					roomBtn.setText("입장");
					if(this.tcpdata.easyStudy[roomBtn.addr]==5) roomBtn.setEnabled(false);
					else roomBtn.setEnabled(true);
				}else {
					roomBtn.setText("만들기");
				}
				repaint();
			}
		}
		
		
//		if(data.msg!=null) {
//			jta.append(data.name + " : "+ data.msg+"\n");
//			System.out.println(data.name +" : "+ data.msg);
//		}
	}
	

}
