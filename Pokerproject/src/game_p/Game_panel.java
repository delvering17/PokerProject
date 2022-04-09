package game_p;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


import lobby_p.Lobby;
import login_p.Login_frame;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.TCPData;



 

public class Game_panel extends JPanel implements ActionListener,NetExecute {
	JButton help ;
	JButton exit;
	
	
	JButton bt; 
	
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JPanel p4;
	JPanel p5;
	
	JLabel timebel;
	
	Timer timer;
	
	JTextField chf;
	JTextArea cht;
	TCPData tcpdata;

	ArrayList<Ingame_userProfile_panel> userprofile = new ArrayList<Ingame_userProfile_panel>();
	Login_frame login_frame;
	Game_panel game_panel;
	PokerGameMain pokerGamemain;
	
	ArrayList<PlayerCard_Label> player1cardShow;
	ArrayList<PlayerCard_Label> player2cardShow;
	ArrayList<JLabel> player3cardShow;
	ArrayList<JLabel> player4cardShow;
	ArrayList<JLabel> player5cardShow;
	Integer num;
	Receiver ch;
	
	public Game_panel(Login_frame login_frame,Receiver ch,TCPData tcpdata) {
		tcpdata.panelChk = "Game";
		this.ch = ch;
		this.ch.game_panel = this;
		this.tcpdata = tcpdata;
		this.login_frame = login_frame;
		game_panel =this;
		num=0;
		while(true) {
			if(tcpdata.playData.get(tcpdata.UserPos)[num] == -1) {
				tcpdata.playData.get(tcpdata.UserPos)[num] = 1;
				break;
			}
			num++;
		}
		if(num==0) {
			JButton GameStart = new JButton("게임 시작");
			GameStart.setBounds(500, 280, 200, 80);
			add(GameStart);
			GameStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for (int i = 2; i < 15; i++) {
						for (int j = 1; j < 5; j++) {
							tcpdata.dealerDeck.add(new PokerCard(i,j));
						}
					}
					tcpdata.DataDestination ="Game";
					split();
					
					ch.send(tcpdata);
					System.out.println("카드 52장 넣음");
					game_panel.remove(GameStart);
					repaint();
				}
			});
		}
		System.out.println(num);
		tcpdata.DataDestination = "enter";
		ch.send(tcpdata);
		setBounds(0, 0, 1200, 800);
		setBackground(new Color(32,56,48));
		setLayout(null);
		
		help = new JButton("help");
		help.setBounds(1040, 0, 70, 30);
		help.setBackground(Color.white);
		help.addActionListener(this);
		add(help);
		
		exit = new JButton("exit");
		exit.setBounds(1110,0,70,30);
		exit.setBackground(Color.white);
		add(exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if (e.getSource() == exit) {
						login_frame.remove(game_panel);
						tcpdata.easyStudy[tcpdata.UserPos]--;
						tcpdata.playData.get(tcpdata.UserPos)[num]=-1;
						ch.send(tcpdata);
						login_frame.add(new Lobby(login_frame,tcpdata,ch));
						login_frame.repaint();
					}
//					dispose(); // 원래있던 창이 꺼지며 새로운 창 나오게함
//					Exit_pg ex_pg = new Exit_pg();
					
			}
			
		});
		

		player1cardShow = new ArrayList<PlayerCard_Label>();
		p1 = new PlayerCard_panel(420, 520, 290, 200);
		add(p1);	
		
		
		ImageIcon img = new ImageIcon("img/card/Card10_2.png");
//		JLabel aa = new JLabel(img);
//		aa.setBounds(20,0,100,200);
		
		
//		p1.add(aa);
		
		
		for (int i = 20 ; i <= 170 ; i += 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 0, 100, 200);
			p1.add(cd);
			player1cardShow.add(cd);
		}
		
		
		p2 = new PlayerCard_panel(200, 50, 290, 200);
		add(p2);
		player2cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 20 ; i <= 170 ; i += 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 0, 100, 200);
			p2.add(cd);
			player2cardShow.add(cd);
		}
//		for(int i = player2cardShow.size()-1 ; i >= 0 ; i--) {
//			p2.add(player2cardShow.get(i));
//		}
		player2cardShow.get(0).setIcon(img);
		
		p3 = new PlayerCard_panel(200,280,290,200);
		player3cardShow = new ArrayList<JLabel>();
		for (int i = 20 ; i <= 170 ; i += 25) {
			player3cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player3cardShow.size()-1 ; i >= 0 ; i--) {
			p3.add(player3cardShow.get(i));
		}
		add(p3);
		p4 = new PlayerCard_panel(710, 50, 290, 200);
		player4cardShow = new ArrayList<JLabel>();
		for (int i = 20 ; i <= 170 ; i += 25) {
			player4cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player4cardShow.size()-1 ; i >= 0 ; i--) {
			p4.add(player4cardShow.get(i));
		}
		add(p4);
		p5 = new PlayerCard_panel(710, 280, 290, 200);
		player5cardShow = new ArrayList<JLabel>();
		for (int i = 20 ; i <= 170 ; i += 25) {
			player5cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player5cardShow.size()-1 ; i >= 0 ; i--) {
			p5.add(player5cardShow.get(i));
		}
		add(p5);

//		ArrayList<JLabel> cardshow = new ArrayList<JLabel>();
	
//		ImageIcon img = new ImageIcon("test/100Card23.png");
//		JLabel card = new JLabel();
//		card.setBounds(170,0, 100,200);
//	
//		
//		JLabel card1 = new JLabel(img);
//		card1.setBounds(145,0, 100,200);
//		p4.add(card1);
//
//		JLabel card2 = new JLabel(img);
//		card2.setBounds(120,0, 100,200);
//		p4.add(card2);
//	
//		JLabel card3 = new JLabel(img);
//		card3.setBounds(95,0, 100,200);
//		p4.add(card3);
//	
//		JLabel card4 = new JLabel(img);
//		card4.setBounds(70,0, 100,200);
//		p4.add(card4);
//		
//		JLabel card5 = new JLabel(img);
//		card5.setBounds(45,0, 100,200);
//		p4.add(card5);
//		JLabel card6 = new JLabel(img);
//		card6.setBounds(20,0, 100,200);
//		p4.add(card6);

		
		// test user profile
		

		
		// betting
		JPanel betting = new JPanel();
		betting.setBounds(500, 50, 200, 200);
		betting.setBackground(new Color(41,67,58));
		add(betting);
		
		JPanel betbuttonarea = new JPanel();
		betbuttonarea.setLayout(null);
        betbuttonarea.setBounds(740,520,170,220);
        add(betbuttonarea);
       
        String [] betbt = {"콜","삥","따당","하프","다이","체크","맥스"};
        	BetBtn bt = new BetBtn("콜",0,0);
        	BetBtn bt1 = new BetBtn("삥",85,0);
        	BetBtn bt2 = new BetBtn("따당",0,55);
        	BetBtn bt3 = new BetBtn("하프",85,55);
        	BetBtn bt4 = new BetBtn("다이",0,110);
        	BetBtn bt5 = new BetBtn("체크",85,110);
        	BetBtn bt6 = new BetBtn("맥스",0,165);
        	
            betbuttonarea.add(bt);
            betbuttonarea.add(bt1);
            betbuttonarea.add(bt2);
            betbuttonarea.add(bt3);
            betbuttonarea.add(bt4);
            betbuttonarea.add(bt5);
            betbuttonarea.add(bt6);
          
//        timebel = new JLabel();
//        timebel.setLayout(new FlowLayout());
//        timebel.setBounds(0, 300, 400, 100);
//        p1.add(timebel);
//        
//        timer = new Timer();
//
//        timer.scheduleAtFixedRate(new TimerTask() {
//        	int i = 5;
//			
//        	@Override
//			public void run() {
//        		timebel.setText("Time left: " + i);
//        		
//                i--;
//
//                if (i < 0) {
//                	timer.cancel();
//                    timebel.setText("Time Over");
//              
//                     
//                }
//                
//			}
//		}, 0, 1000);
        
        
        
        
    	JPanel chat = new JPanel();
    	chat.setLayout(new BorderLayout());
		chat.setBounds(920,520,250,220);
		chat.setBackground(Color.black);
		add(chat);
		
		
		chf = new JTextField();
		chf.setBounds(0,200, 250, 20);
		chf.addActionListener(this);
		
		cht = new JTextArea();
		JScrollPane jp = new JScrollPane(cht);
		jp.setBounds(0, 0, 250, 200);
		cht.setEditable(false);
		
		chat.add(chf,"South");
		chat.add(jp,"Center");
		
		chf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tcpdata.DataDestination = "Chatting";
					tcpdata.msg = chf.getText();
					ch.send(tcpdata);
					cht.setCaretPosition(cht.getDocument().getLength());
					chf.setText("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//				tcpdata.DataDestination = "Game";
//				tcpdata.panelChk = "Game";
//				ch.send(tcpdata);
//				cht.setCaretPosition(cht.getDocument().getLength());
//				chf.setText("");
			}
		});
  

		
   }



	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == help) {
			Help_pg pg = new Help_pg();
		}	
		
		
	}


	class BetBtn extends JButton {
		public BetBtn(String name,int x,int y) {
			super(name);
			setBounds(x, y,85, 55);
			setBackground(Color.white);
		}
	}


	@Override
	public void execute(TCPData data) {
		System.out.println("왔니?");
		this.tcpdata.playData = data.playData;
		switch (data.DataDestination) {
		case "enter":
			this.tcpdata.playData = data.playData;
			break;
		case "Chatting":
			if(data.UserPos==this.tcpdata.UserPos) {
				cht.append(data.name+" : "+data.msg+"\n");
			}
			break;
		case "Game":
			System.out.println("왔니?2");
			this.tcpdata.playerDeck = data.playerDeck;
			System.out.println(this.tcpdata.playerDeck.get(0).get(0).number);
			ImageIcon img = new ImageIcon(this.tcpdata.playerDeck.get(0).get(0).imgname);
			player3cardShow.get(0).setIcon(img);
			
	    	this.repaint();
	    	p3.repaint();
			break;
		}
		
	}
	
	void split() {
		
		
		tcpdata.playerDeck.put(num,new ArrayList<PokerCard>());
		tcpdata.playData.get(tcpdata.UserPos);
		int chkd = 0;
		while(true) {
			Random number = new Random();
			int a = number.nextInt(tcpdata.dealerDeck.size());
			if(chkd==4) {
				break;
			}
			if(tcpdata.playData.get(tcpdata.UserPos)[chkd] != -1) {
				tcpdata.playerDeck.get(chkd).add(tcpdata.dealerDeck.get(a));
				tcpdata.dealerDeck.remove(a);
			}
			chkd++;
		}
		tcpdata.DataDestination = "Game";
		ch.send(tcpdata);
	}
}	
	
class Help_pg extends JFrame{
	
	JLabel p;

	public Help_pg() {
		
		setBounds(1000,300,660,660);
		setTitle("사용 설명");
		
		ImageIcon img = new ImageIcon("gamepanel/explain.png");
		p = new JLabel(img);
		add(p);
		setVisible(true);
		setResizable(false);
		
		
	}
	


}	

class PlayerCard_panel extends JPanel {
	
	public PlayerCard_panel(int x, int y, int width, int height) {
		setBounds(x, y, width, height);
		setBackground(new Color(41,67,58));
		setLayout(null);
	}
}

class PlayerCard_Label extends JLabel {
	
	public PlayerCard_Label(int x, int y, int width, int height) {
		setBounds(x, y, width, height);

	}
}

