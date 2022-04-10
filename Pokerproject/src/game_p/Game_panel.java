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
	
	BetBtn bt ;
	BetBtn bt1 ;
	BetBtn bt2 ;
	BetBtn bt3 ;
	BetBtn bt4 ;
	BetBtn bt5 ;
	BetBtn bt6 ;
	
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JPanel p4;
	JPanel p5;
	
	JLabel betting;
	
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
	
	Ingame_userProfile_panel Ingame_userProfile_panel_0;
	Ingame_userProfile_panel Ingame_userProfile_panel_1;
	Ingame_userProfile_panel Ingame_userProfile_panel_2;
	Ingame_userProfile_panel Ingame_userProfile_panel_3;
	Ingame_userProfile_panel Ingame_userProfile_panel_4;
	
	ArrayList<String> ipjang = new ArrayList<String>();
	
	public ArrayList<Integer> bettingMoney = new ArrayList<Integer>();
	public int panMoney = 10;
	public int wholeBettingMoney = 0;
	public String winner;
	
	public Game_panel(Login_frame login_frame,Receiver ch,TCPData tcpdata,Integer addr) {
		tcpdata.panelChk = "Game";
		this.ch = ch;
		this.ch.game_panel = this;
		this.ch.lobby_panel = null;
		this.tcpdata = tcpdata;
		this.login_frame = login_frame;
		game_panel =this;
		tcpdata.easyStudy[addr]++;
		tcpdata.UserPos = addr;
		tcpdata.DataDestination = "Chatting";
		num=0;
		while(true) {
			if(tcpdata.playData.get(tcpdata.UserPos)[num] == -1) {
				tcpdata.playData.get(tcpdata.UserPos)[num] = 1;
				break;
			}
			num++;
		}
		tcpdata.msg = addr+"번방,"+num+"번 플레이어 "+"[입장]";
//		for (Integer a : tcpdata.playData.get(tcpdata.UserPos)) {
//			System.out.println(a);
//			
//		}
//		ch.send(tcpdata);
		
		
		
		if(num==0) {
			JButton GameStart = new JButton("게임 시작");
			GameStart.setBounds(500, 280, 200, 80);
			add(GameStart);
			GameStart.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("프로필 보여주냐");
					tcpdata.DataDestination = "Game";
					tcpdata.msg = "profileShow";
					tcpdata.ipjang = ipjang;
					ch.send(tcpdata);
					
					for (int i = 2; i < 15; i++) {
						for (int j = 1; j < 5; j++) {
							tcpdata.dealerDeck.add(new PokerCard(i,j));
						}
					}

					GameProcess();

//					System.out.println("카드 52장 넣음");
					game_panel.remove(GameStart);
					
					repaint();
					
				}
			});
			
		}
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
					tcpdata.easyStudy[tcpdata.UserPos]--;
					tcpdata.playData.get(tcpdata.UserPos)[num]=-1;
					login_frame.add(new Lobby(login_frame,tcpdata,ch));
					login_frame.remove(game_panel);
					login_frame.repaint();
				}
			}
		});
		

		player1cardShow = new ArrayList<PlayerCard_Label>();
		p1 = new PlayerCard_panel(420, 520, 290, 200);
		add(p1);	
		
		ImageIcon img = new ImageIcon("img/card/Card10_2.png");
		
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 0, 100, 200);
			p1.add(cd);
			player1cardShow.add(cd);
		}
		
		
		p2 = new PlayerCard_panel(200, 50, 290, 200);
		add(p2);
		player2cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 0, 100, 200);
			p2.add(cd);
			player2cardShow.add(cd);
		}


		p3 = new PlayerCard_panel(200,280,290,200);
		player3cardShow = new ArrayList<JLabel>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			player3cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player3cardShow.size()-1 ; i >= 0 ; i--) {
			p3.add(player3cardShow.get(i));
		}
		add(p3);
		p4 = new PlayerCard_panel(710, 50, 290, 200);
		player4cardShow = new ArrayList<JLabel>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			player4cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player4cardShow.size()-1 ; i >= 0 ; i--) {
			p4.add(player4cardShow.get(i));
		}
		add(p4);
		p5 = new PlayerCard_panel(710, 280, 290, 200);
		player5cardShow = new ArrayList<JLabel>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
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
		betting = new JLabel();
		betting.setBounds(500, 50, 200, 200);
		betting.setBackground(new Color(41,67,58));
		betting.setText("-----");
		
		add(betting);
		
		JPanel betbuttonarea = new JPanel();
		betbuttonarea.setLayout(null);
        betbuttonarea.setBounds(740,520,170,220);
        add(betbuttonarea);
       
        String [] betbt = {"콜","삥","따당","하프","다이","체크","맥스"};
    	bt = new BetBtn("콜",0,0);
    	bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				System.out.println("콜 누름");
				
				tcpdata.DataDestination = "Game";
				tcpdata.bettingMoney.set(num, panMoney);
				tcpdata.wholeBettingMoney += panMoney;
				tcpdata.msg = num+":betting_call";
				
				
				ch.send(tcpdata);
				
				
			}
		});
    	bt1 = new BetBtn("삥",85,0);
    	bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tcpdata.DataDestination = "Game";
				tcpdata.bettingMoney.set(num, panMoney);
				tcpdata.wholeBettingMoney += panMoney;
				tcpdata.msg = num+":betting";
				
				ch.send(tcpdata);
				
			}
		});
    	bt2 = new BetBtn("따당",0,55);
    	bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	bt3 = new BetBtn("하프",85,55);
    	bt3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	bt4 = new BetBtn("다이",0,110);
    	bt4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	bt5 = new BetBtn("체크",85,110);
    	bt5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	bt6 = new BetBtn("맥스",0,165);
    	bt6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
    	
        betbuttonarea.add(bt);
        betbuttonarea.add(bt1);
        betbuttonarea.add(bt2);
        betbuttonarea.add(bt3);
        betbuttonarea.add(bt4);
        betbuttonarea.add(bt5);
        betbuttonarea.add(bt6);
        
        
        //profile
        
        
        
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
    	chat.setLayout(null);
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
		
		chat.add(chf);
		chat.add(jp);
		
		chf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tcpdata.DataDestination = "Chatting";
					tcpdata.msg = chf.getText();
					ch.send(tcpdata);
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
  
		ch.send(tcpdata);
		
//		tcpdata.DataDestination = "profileShow";
//		tcpdata.msg = num+":profileShow";
//		ch.send(tcpdata);
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
		this.tcpdata.playData = data.playData;
		this.tcpdata.playerDeck = data.playerDeck;
		this.tcpdata.easyStudy = data.easyStudy;
		this.tcpdata.userName = data.userName;
		
		if(data.UserPos==this.tcpdata.UserPos) {
			cht.append(data.name+" : "+data.msg+"\n");
		}
		if(data.msg.contains("[입장]")) {
			if (ipjang.contains(data.name)) {
				System.out.println("이미" + data.name+"있음");
			} else {
				ipjang.add(data.name);
				System.out.println("입장 넣음:" + data.name);
				System.out.println(ipjang.toString());
			}
			
		}
		repaint();
	
		switch (data.DataDestination) {
		case "enter":
			this.tcpdata.playData = data.playData;
			break;
		case "Game":
			ImageIcon img;

			
			switch (data.msg) {
			
			case "카드 나와라":
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				
				for (int i =6 ; i >= 5 ; i--) {
					img = new ImageIcon("img/card/CardBackImg.png");
					player1cardShow.get(i).setIcon(img);
				}
				for (int i =4 ; i >= 0 ; i--) {
					img = new ImageIcon(data.playerDeck.get(0).get(i).imgname);
					player1cardShow.get(i).setIcon(img);
				}
				for (int i =6 ; i >= 5 ; i--) {
					img = new ImageIcon("img/card/CardBackImg.png");
					player2cardShow.get(i).setIcon(img);
				}
				for (int i =4 ; i >= 0 ; i--) {
					img = new ImageIcon(data.playerDeck.get(1).get(i).imgname);
					player2cardShow.get(i).setIcon(img);
				}
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				player0turn();
				break;
			case "0:betting" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);

				player1turn();
				
				
				break;
			case "1:betting" :
				
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				
				player0turn();
		
				
				
				break;
			case "0:betting_call":
				resultWinner();
				break;
				
			case "1:betting_call":	
				resultWinner();
				break;	
				
			case "0:profileShow":
				// 닉네임으로 불러오기
				
				break;
			
			default :
				System.out.println("빠져나감");
				break;
			} 
			
			if (data.msg.contains("profileShow")) {
				System.out.println("프프프");
				System.out.println(data.ipjang.toString());
				// 닉네임으로 불러오기
//				if (Ingame_userProfile_panel_0 != null ) {
//					this.remove(Ingame_userProfile_panel_0);
//				}
//				if (Ingame_userProfile_panel_1 != null ) {
//					this.remove(Ingame_userProfile_panel_1);
//				} 
//				if (Ingame_userProfile_panel_2 != null ) {
//					this.remove(Ingame_userProfile_panel_2);
//				} 
//				if (Ingame_userProfile_panel_3 != null ) {
//					this.remove(Ingame_userProfile_panel_3);
//				} 
//				if (Ingame_userProfile_panel_4 != null ) {
//					this.remove(Ingame_userProfile_panel_4);
//				} 
				System.out.println();
				for (int i = 0 ; i < data.ipjang.size() ; i++) {
					System.out.println("자 들갑니다");
					System.out.println(data.ipjang.get(i));
					switch (i) {
					case 0:
						Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(i,data.ipjang.get(i));
						add(Ingame_userProfile_panel_0);
						break;
					case 1:
						Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(i,data.ipjang.get(i));
						add(Ingame_userProfile_panel_1);
						break;	
					case 2:
						Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(i,data.ipjang.get(i));
						add(Ingame_userProfile_panel_2);
						break;
					case 3:
						Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(i,data.ipjang.get(i));
						add(Ingame_userProfile_panel_3);
						break;
					case 4:
						Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(i,data.ipjang.get(i));
						add(Ingame_userProfile_panel_4);
						break;
						
						
					}
				} 
					
					
//				int num = Integer.parseInt(data.msg.split("_")[1].split(",")[0]);
//				String nick = data.msg.split("_")[1].split(",")[1];
//				switch (num) {
//				case 0:
//					Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(num,nick);
//					add(Ingame_userProfile_panel_0);
//					break;
//				case 1:
//					Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(num,nick);
//					add(Ingame_userProfile_panel_1);
//					break;	
//				case 2:
//					Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(num,nick);
//					add(Ingame_userProfile_panel_2);
//					break;
//				case 3:
//					Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(num,nick);
//					add(Ingame_userProfile_panel_3);
//					break;
//				case 4:
//					Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(num,nick);
//					add(Ingame_userProfile_panel_4);
//					break;
//					
//					
//				}
					
			
			}
			
	    	repaint();
	    
			break;
//		case "profileShow": 	
//			System.out.println("프로필 보여줌");
//			
//			repaint();
//			break;
		}
		
	}
	
//	void cardShow() {
//		
//		switch () {
//		
//		case :
//			break;
//		
//		}
//		
//	}
	
	void split() {
		tcpdata.wholeBettingMoney = ipjang.size()*panMoney; 
		tcpdata.panMoney = panMoney;
		for (int i = 0 ; i < ipjang.size() ; i++) {
			tcpdata.bettingMoney.add(0);			
		}
		
		tcpdata.playerDeck.put(0,new ArrayList<PokerCard>());
		tcpdata.playerDeck.put(1,new ArrayList<PokerCard>());
		tcpdata.playData.get(tcpdata.UserPos);
		int chkd = 0;
//		while(true) {
//			Random number = new Random();
//			int a = number.nextInt(tcpdata.dealerDeck.size());
//			if(chkd==2) {
//				break;
//			}
//			if(tcpdata.playData.get(tcpdata.UserPos)[chkd] != -1) {
//				tcpdata.playerDeck.get(chkd).add(tcpdata.dealerDeck.get(a));
//				tcpdata.dealerDeck.remove(a);
//			}
//			chkd++;
//		}
		int people = 0;
		
		while(true) {
			
			if (people == 2) {
				break;
			}
			for (int i = 0 ; i < 7 ;  i++) {
				Random number = new Random();
				int a = number.nextInt(tcpdata.dealerDeck.size());
				tcpdata.playerDeck.get(people).add(tcpdata.dealerDeck.get(a));
				tcpdata.dealerDeck.remove(a);			
			}
			
			
			people ++;
		}
//		System.out.println("0번");
//		for (PokerCard aa : tcpdata.playerDeck.get(0)) {
//			System.out.println(aa.number + ", " + aa.shape);
//		}
//		System.out.println("1번");
//		for (PokerCard aa : tcpdata.playerDeck.get(1)) {
//			System.out.println(aa.number + ", " + aa.shape);
//		}
		

//		System.out.println(new Jokbo().jokbo(tcpdata.playerDeck.get(0)));
//		System.out.println(new Jokbo().jokbo(tcpdata.playerDeck.get(1)));
		
		tcpdata.DataDestination = "Game";
		tcpdata.msg = "카드 나와라";
		ch.send(tcpdata);
		
	}
	
	void GameProcess() {
		// 카드  7장 나눠주기
		split();
		// 0번째 턴
		
		
	}
	
	void player0turn() {
        System.out.println("0번 플레이어 배팅해주세요");
        if (num == 0) {
            betting_Button_true();

        } else {
            betting_Button_false();
        }


    }
	void player1turn() {
        System.out.println("1번 플레이어 배팅해주세요");
        if (num == 1) {
            betting_Button_true();

        } else {
            betting_Button_false();
        }
    }
	
    void betting_Button_false (){
        bt.setEnabled(false);
        bt1.setEnabled(false);
        bt2.setEnabled(false);
        bt3.setEnabled(false);
        bt4.setEnabled(false);
        bt5.setEnabled(false);
        bt6.setEnabled(false);

    }
    void betting_Button_true (){
        bt.setEnabled(true);
        bt1.setEnabled(true);
        bt2.setEnabled(true);
        bt3.setEnabled(true);
        bt4.setEnabled(true);
        bt5.setEnabled(true);
        bt6.setEnabled(true);

    }
    
    
    
    
    
    void resultWinner() {
    	for (int i = 0 ; i < tcpdata.ipjang.size() ; i ++) {
    		tcpdata.res[i] = new Jokbo().jokbo(tcpdata.playerDeck.get(i));
    		System.out.println(tcpdata.res.toString());
    	}
						
    	
    	ArrayList<String> bb = new ArrayList<String>();
    
    			bb.add("2탑"	);
    			bb.add("3탑"	);
    			bb.add("4탑"	);
    			bb.add("5탑"	);
    			bb.add("6탑"	);
    			bb.add("7탑"	);
    			bb.add("8탑"	);
    			bb.add("9탑"	);
    			bb.add("10탑");
    			bb.add("11탑");
    			bb.add("12탑");
    			bb.add("13탑");
    			bb.add("14탑");
    			bb.add("원페어");
    			bb.add("투페어");
    			bb.add("트리플");
    			bb.add("스트레이트"	);
    			bb.add("백스트레이트");
    			bb.add("로얄스트레이트");
    			bb.add("플러쉬");
    			bb.add("풀하우스");
    			bb.add("포카드");
    			bb.add("스트레이트플러쉬");
    			bb.add("백스트레이트플러쉬");
    			bb.add("로얄스트레이트플러쉬");


    			int[] test = new int[tcpdata.res.length];
    			for (int i = 0; i < test.length; i++) {
    				for (int j = 0; j < bb.size(); j++) {
    					if(bb.get(j)==tcpdata.res[i]) {
    						test[i]=j; 
    					}
    				}
    			}

    			int num = -1;
    			int aaa = -1;
    			for (int i = 0; i < test.length; i++) {
    				if(test[i]>aaa) {
    					aaa=test[i];
    					num = i;
    				}
    			}
    			
    
    	
    	
    	tcpdata.DataDestination = "Chatting";
		tcpdata.msg = "승: "+num ;	
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

