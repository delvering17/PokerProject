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
import java.util.Map;
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

import DB_p.SignDB;
import lobby_p.Lobby;
import login_p.Login_frame;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.TCPData;





public class Game_panel extends JPanel implements ActionListener,NetExecute {
	JButton help ;
	JButton exit;
	
	Jokbo jokbbo;
	JokBoTEST jk;
	
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

	JLabel p1_turn;
	JLabel p1_bet_jokbo;
	JLabel p2_turn;
	JLabel p2_bet_jokbo;
	JLabel p3_turn;
	JLabel p3_bet_jokbo;
	JLabel p4_turn;
	JLabel p4_bet_jokbo;
	JLabel p5_turn;
	JLabel p5_bet_jokbo;
	
	JLabel turnNickname;
	
	ArrayList<Ingame_userProfile_panel> userprofile = new ArrayList<Ingame_userProfile_panel>();
	Login_frame login_frame;
	Game_panel game_panel;
	PokerGameMain pokerGamemain;
	
	ArrayList<PlayerCard_Label> player1cardShow;
	ArrayList<PlayerCard_Label> player2cardShow;
	ArrayList<PlayerCard_Label> player3cardShow;
	ArrayList<PlayerCard_Label> player4cardShow;
	ArrayList<PlayerCard_Label> player5cardShow;
	ArrayList<ArrayList<PlayerCard_Label>> ttt;
	Integer num;
	Receiver ch;
	
	Ingame_userProfile_panel Ingame_userProfile_panel_0;
	Ingame_userProfile_panel Ingame_userProfile_panel_1;
	Ingame_userProfile_panel Ingame_userProfile_panel_2;
	Ingame_userProfile_panel Ingame_userProfile_panel_3;
	Ingame_userProfile_panel Ingame_userProfile_panel_4;
	
	ArrayList<String> ipjang = new ArrayList<String>();
	
	public ArrayList<Integer> bettingMoney = new ArrayList<Integer>();
	public int panMoney;
	public int wholeBettingMoney;
//	public String winner;
	ImageIcon default_bet = new ImageIcon("img/gamepanel/default.png");
	ImageIcon cdback  = new ImageIcon("img/card/CardBackimg.png");
	public Game_panel(Login_frame login_frame,Receiver ch,TCPData tcpdata,Integer addr) {
		wholeBettingMoney=0;
		panMoney = 10;
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
		tcpdata.userNumber.add(num);
		tcpdata.msg = addr+"번방,"+num+"번 플레이어 "+"[입장]";
		
		tcpdata.test.get(tcpdata.UserPos).put(num, tcpdata.name);
		
		if(num==0) {
			JButton GameStart = new JButton("게임 시작");
			GameStart.setBounds(500, 280, 200, 80);
			add(GameStart);
			GameStart.addActionListener(new ActionListener() {
				//게임 시작 버튼
				@Override
				public void actionPerformed(ActionEvent e) {
					betting_Button_true();
					tcpdata.DataDestination = "Game";
					tcpdata.userCount = num;
					for (int i = 2; i < 15; i++) {
						for (int j = 1; j < 5; j++) {
							tcpdata.dealerDeck.add(new PokerCard(i,j));
						}
					}
					tcpdata.roomclose.replace(tcpdata.UserPos, true);
					GameProcess();
					game_panel.remove(GameStart);
					repaint();
				}
			});
			
		}
		jokbbo = new Jokbo();
		jk = new JokBoTEST();
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
					tcpdata.test.get(tcpdata.UserPos).remove(num); 
					login_frame.add(new Lobby(login_frame,tcpdata,ch));
					login_frame.remove(game_panel);
					login_frame.repaint();
				}
			}
		});
		

		p1 = new PlayerCard_panel(420, 520, 290, 200);
		add(p1);	
		p1_turn = new JLabel();
		ImageIcon turn = new ImageIcon("img/gamepanel/default.png");
		p1_turn.setBounds(0,0,35,35);
		p1_turn.setIcon(turn);
		p1_bet_jokbo = new JLabel();
		p1_bet_jokbo.setBounds(40,0,200,35);
		p1_bet_jokbo.setForeground(Color.white);
		p1_bet_jokbo.setFont(p1_bet_jokbo.getFont().deriveFont(16.0f));
		p1_bet_jokbo.setText("베팅 금액: 0");
		
		player1cardShow = new ArrayList<PlayerCard_Label>();
		
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 20, 100, 200);
			p1.add(cd);
			player1cardShow.add(cd);
		}
		p1.add(p1_turn);
		p1.add(p1_bet_jokbo);

		
		
		p2 = new PlayerCard_panel(200, 50, 290, 200);
		add(p2);
		p2_turn = new JLabel();
		turn = new ImageIcon("img/gamepanel/default.png");
		p2_turn.setBounds(0,0,35,35);
		p2_turn.setIcon(turn);
		p2_bet_jokbo = new JLabel();
		p2_bet_jokbo.setBounds(40,0,200,35);
		p2_bet_jokbo.setForeground(Color.white);
		p2_bet_jokbo.setFont(p2_bet_jokbo.getFont().deriveFont(16.0f));
		p2_bet_jokbo.setText("베팅 금액: 0");
		
		player2cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 20, 100, 200);
			p2.add(cd);
			player2cardShow.add(cd);
		}
		p2.add(p2_turn);
		p2.add(p2_bet_jokbo);

		p3 = new PlayerCard_panel(200,280,290,200);
		add(p3);
		p3_turn = new JLabel();
		turn = new ImageIcon("img/gamepanel/default.png");
		p3_turn.setBounds(0,0,35,35);
		p3_turn.setIcon(turn);
		p3_bet_jokbo = new JLabel();
		p3_bet_jokbo.setBounds(40,0,200,35);
		p3_bet_jokbo.setForeground(Color.white);
		p3_bet_jokbo.setFont(p3_bet_jokbo.getFont().deriveFont(16.0f));
		p3_bet_jokbo.setText("베팅 금액: 0");
		
		player3cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 20, 100, 200);
			p3.add(cd);
			player3cardShow.add(cd);
		}
		p3.add(p3_turn);
		p3.add(p3_bet_jokbo);
		
		
		p4 = new PlayerCard_panel(710, 50, 290, 200);
		add(p4);
		p4_turn = new JLabel();
		turn = new ImageIcon("img/gamepanel/default.png");
		p4_turn.setBounds(0,0,35,35);
		p4_turn.setIcon(turn);
		p4_bet_jokbo = new JLabel();
		p4_bet_jokbo.setBounds(40,0,200,35);
		p4_bet_jokbo.setForeground(Color.white);
		p4_bet_jokbo.setFont(p4_bet_jokbo.getFont().deriveFont(16.0f));
		p4_bet_jokbo.setText("베팅 금액: 0");
		
		player4cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 20, 100, 200);
			p4.add(cd);
			player4cardShow.add(cd);
		}
		p4.add(p4_turn);
		p4.add(p4_bet_jokbo);
		
		p5 = new PlayerCard_panel(710, 280, 290, 200);
		add(p5);
		p5_turn = new JLabel();
		turn = new ImageIcon("img/gamepanel/default.png");
		p5_turn.setBounds(0,0,35,35);
		p5_turn.setIcon(turn);
		p5_bet_jokbo = new JLabel();
		p5_bet_jokbo.setBounds(40,0,200,35);
		p5_bet_jokbo.setForeground(Color.white);
		p5_bet_jokbo.setFont(p5_bet_jokbo.getFont().deriveFont(16.0f));
		p5_bet_jokbo.setText("베팅 금액: 0");
		
		player5cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			PlayerCard_Label cd = new PlayerCard_Label(i, 20, 100, 200);
			p5.add(cd);
			player5cardShow.add(cd);
		}
		p5.add(p5_turn);
		p5.add(p5_bet_jokbo);
		
		ttt = new ArrayList<ArrayList<PlayerCard_Label>>();

		

		
		// betting
		betting = new JLabel();
		betting.setBounds(500, 50, 200, 200);
		betting.setForeground(Color.white);
		betting.setBackground(new Color(41,67,58));
		betting.setText("-----");
		
		add(betting);
		
		JPanel betbuttonarea = new JPanel();
		betbuttonarea.setLayout(null);
        betbuttonarea.setBounds(740,520,170,220);
        add(betbuttonarea);
        
        //String [] betbt = {"콜","삥","따당","하프","다이","체크","맥스"};
        ImageIcon i0 = new ImageIcon("img/gamepanel/call_bt.gif");
        bt = new BetBtn("",0,0); // 콜
        bt.setIcon(i0);
    	bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
					tcpdata.DataDestination = "Game";
					tcpdata.btMoney = tcpdata.prebetMoney;
					tcpdata.prebetMoney = tcpdata.btMoney;
					tcpdata.wholeBettingMoney += tcpdata.btMoney;
					tcpdata.money -= tcpdata.btMoney;
					tcpdata.msg = "betting_call";
					tcpdata.callCount++;
					tcpdata.userCount = num;
					
					if(tcpdata.callCount == tcpdata.test.get(tcpdata.UserPos).keySet().size()-1&&tcpdata.playerDeck.get(0).size()==7) {
						for (Integer z : tcpdata.test.get(tcpdata.UserPos).keySet()) {
							
							tcpdata.result.put(z, jokbbo.jokbo(tcpdata.playerDeck.get(z)));
						}
						
						tcpdata.winner = jk.resultWinner(tcpdata.result)+"";
						tcpdata.last = true;
						ch.send(tcpdata);
					}else if(tcpdata.callCount == tcpdata.test.get(tcpdata.UserPos).keySet().size()-1){
						
						oneSplit();
					}else{
						ch.send(tcpdata);
					}
				
				
			}
		});
    	ImageIcon i1 = new ImageIcon("img/gamepanel/bbing_bt.gif");
        bt1 = new BetBtn("",85,0); // 삥
        bt1.setIcon(i1);
    	bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
		
					tcpdata.callCount=0;
					tcpdata.userCount = num;
					tcpdata.DataDestination = "Game";
					tcpdata.prebetMoney = tcpdata.btMoney;
					tcpdata.btMoney = panMoney;
					tcpdata.wholeBettingMoney += tcpdata.btMoney;
					tcpdata.money -= tcpdata.btMoney;
					tcpdata.msg = "betting_bbing";
					
					ch.send(tcpdata);
				
				
			}
		});
    	ImageIcon i2 = new ImageIcon("img/gamepanel/ddadang_bt.gif");
        bt2 = new BetBtn("",0,55); //따당
        bt2.setIcon(i2);
    	bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tcpdata.callCount=0;
				tcpdata.userCount = num;
				tcpdata.DataDestination = "Game";
				tcpdata.btMoney = tcpdata.prebetMoney*2; 
				tcpdata.prebetMoney = tcpdata.btMoney;
				tcpdata.wholeBettingMoney += tcpdata.btMoney;
				tcpdata.money -= tcpdata.btMoney;
				tcpdata.msg = "betting_ddadang";
				
				ch.send(tcpdata);
				
			}
		});
    	ImageIcon i3 = new ImageIcon("img/gamepanel/half_bt.gif");
        bt3 = new BetBtn("",85,55);//하프
        bt3.setIcon(i3);
    	bt3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tcpdata.callCount=0;
				tcpdata.userCount = num;
				tcpdata.DataDestination = "Game";
				tcpdata.btMoney = tcpdata.wholeBettingMoney/2; 
				tcpdata.prebetMoney = tcpdata.btMoney;
				tcpdata.wholeBettingMoney += tcpdata.btMoney;
				tcpdata.money -= tcpdata.btMoney;
				tcpdata.msg = "betting_half";
				
				ch.send(tcpdata);
				
			}
		});
    	ImageIcon i4 = new ImageIcon("img/gamepanel/die_bt.gif");
        bt4 = new BetBtn("",0,110);//다이
        bt4.setIcon(i4);
        bt4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                
                
                tcpdata.userCount = num;
                tcpdata.DataDestination = "Game";
                tcpdata.msg = "betting_die";

                ch.send(tcpdata);
            }
        });
        ImageIcon i5 = new ImageIcon("img/gamepanel/quarter_bt.gif");
        bt5 = new BetBtn("",85,110);//쿼터
        bt5.setIcon(i5);
        bt5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            
        	  tcpdata.callCount=0;
              tcpdata.userCount = num;
              tcpdata.DataDestination = "Game";
              tcpdata.btMoney = tcpdata.wholeBettingMoney/4; 
              tcpdata.prebetMoney = tcpdata.btMoney;
              tcpdata.wholeBettingMoney += tcpdata.btMoney;
              tcpdata.money -= tcpdata.btMoney;
              
              tcpdata.msg = "betting_quarter";

              ch.send(tcpdata);
            }
        });
        ImageIcon i6 = new ImageIcon("img/gamepanel/max_bt.gif");
        bt6 = new BetBtn("",0,165);//맥스
        bt6.setIcon(i6);
    	bt6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tcpdata.callCount = 0 ;
				tcpdata.userCount = num;
				tcpdata.DataDestination = "Game";
				tcpdata.btMoney = (int)tcpdata.money;
				tcpdata.prebetMoney = tcpdata.btMoney;
				tcpdata.wholeBettingMoney += tcpdata.btMoney ;
				tcpdata.money -= tcpdata.btMoney;
				
			      tcpdata.msg = "betting_max";
				
				ch.send(tcpdata);
			}
		});
    	
        betbuttonarea.add(bt);
        betbuttonarea.add(bt1);
        betbuttonarea.add(bt2);
        betbuttonarea.add(bt3);
        betbuttonarea.add(bt4);
        betbuttonarea.add(bt5);
        betbuttonarea.add(bt6);
        
        
//		  profile		나 타이머야 !!
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
        
		//
		turnNickname = new JLabel();
		turnNickname.setBounds(500, 300, 200, 200);
		turnNickname.setForeground(Color.white);
		turnNickname.setText("차례: nickname");
		turnNickname.setHorizontalAlignment(JLabel.CENTER);
		turnNickname.setFont(turnNickname.getFont().deriveFont(16.0f));
		add(turnNickname);
		
		//
		

        
        
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
					e1.printStackTrace();
				}
			}
		});
		
		betting_Button_false ();
		
		ttt.add(player1cardShow);
		ttt.add(player2cardShow);
		ttt.add(player3cardShow);
		ttt.add(player4cardShow);
		ttt.add(player5cardShow);
		ch.send(tcpdata);
		
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

	//큐트시작
	@Override
	public void execute(TCPData data) {
		this.tcpdata.playData = data.playData;
		this.tcpdata.playerDeck = data.playerDeck;
		this.tcpdata.easyStudy = data.easyStudy;
		this.tcpdata.userName = data.userName;
		this.tcpdata.test = data.test;
		if(data.UserPos==this.tcpdata.UserPos) {
			this.tcpdata.dealerDeck = data.dealerDeck;
			cht.append(data.name+" : "+data.msg+"\n");
			this.tcpdata.prebetMoney = data.prebetMoney;
			this.tcpdata.userCount = data.userCount;
			this.tcpdata.callCount = data.callCount;
			this.tcpdata.last = data.last;
			this.tcpdata.userNumber = data.userNumber;
		}

		if (data.msg.contains("[입장]") ) {
			System.out.println("프로필 생성");
			
//			for () {
//				
//				new Ingame_userProfile_panel(); // playNum, nickname
//				
//			}
			for (Map.Entry<Integer, String> entry : data.test.get(data.UserPos).entrySet()) {
				switch (entry.getKey()) {
				
				case 0:
					if (Ingame_userProfile_panel_0 == null) {
						Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,entry.getValue());
						add(Ingame_userProfile_panel_0);
					}
					break;
				case 1:
					if (Ingame_userProfile_panel_1 == null) {
						Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1,entry.getValue());
						add(Ingame_userProfile_panel_1);
					}
					break;
				case 2:
					if (Ingame_userProfile_panel_2 == null) {
						Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2,entry.getValue());
						add(Ingame_userProfile_panel_2);
					}
					break;
				case 3:
					if (Ingame_userProfile_panel_3 == null) {
						Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3,entry.getValue());
						add(Ingame_userProfile_panel_3);
					}
					break;
				case 4:
					if (Ingame_userProfile_panel_4 == null) {
						Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4,entry.getValue());
						add(Ingame_userProfile_panel_4);
					}	
					break;
				}
			}

//			if (Ingame_userProfile_panel_0 == null) {
//				Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,);
//			}
			
	
		}
		//
		switch (data.DataDestination) {
	
		case "Game":
			
			int go = 0;
			if (data.msg.contains("betting")) {
				
				
				for (Map.Entry<Integer, String> entry : data.test.get(data.UserPos).entrySet()) {
					if (data.name.equals(entry.getValue())) {
						go = entry.getKey();
					}
				}
				switch(go) {
				case 0:
					p1_bet_jokbo.setText("베팅 : "+ data.btMoney);
					betting_profileReset (data, 0);
					whatBetting(data.msg.split("_")[1],go);
					break;
				case 1:
					p2_bet_jokbo.setText("베팅 : "+ data.btMoney);
					betting_profileReset (data, 1);
					whatBetting(data.msg.split("_")[1],go);
					break;
				case 2:
					p3_bet_jokbo.setText("베팅 : "+ data.btMoney);
					betting_profileReset (data, 2);
					whatBetting(data.msg.split("_")[1],go);
					break;
				case 3:
					p4_bet_jokbo.setText("베팅 : "+ data.btMoney);
					betting_profileReset (data, 3);
					whatBetting(data.msg.split("_")[1],go);
					break;
				case 4:
					p5_bet_jokbo.setText("베팅 : "+ data.btMoney);
					betting_profileReset (data, 4);
					whatBetting(data.msg.split("_")[1],go);
					break;
				}
				
			
				
			}
			
			switch (data.msg) {
			
			case "카드 나와라":
				//확인필요
				for (Integer z : data.test.get(tcpdata.UserPos).keySet()) {
					for (Integer i = 2; i >= 0; i--) {
						if((!z.equals(num))&& i<2) {
							ttt.get(z).get(i).setIcon(cdback);														
						}else {
							ttt.get(z).get(i).setIcon(data.playerDeck.get(z).get(i).img);							
						}
					}
				}
				bt.setEnabled(false);

				break;
			case "betting_bbing" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				cht.append("전체 베팅금액: "+data.wholeBettingMoney+ ", 배팅 머니: "+ data.btMoney);
				playerturn();
				
				break;
			case "betting_call":
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				cht.append("전체 베팅금액 : "+data.wholeBettingMoney+ ", 배팅 머니 : "+ data.btMoney+", 내 머니 : "+data.money);
				playerturn();
				if(tcpdata.callCount == tcpdata.test.get(tcpdata.UserPos).keySet().size()-1&&tcpdata.playerDeck.get(0).size()==7&&tcpdata.last) {
					System.out.println("종료입니다.");
					System.out.println("집가자 가고싶어요 보내줘요" + data.winner);
					
					// DB 입력 
				
					
					
					//
					// 승패 입력
					if (num == Integer.parseInt(data.winner)) {
						System.out.println("승자");
						this.tcpdata.win ++;
						this.tcpdata.totalGame ++;
						this.tcpdata.money += data.wholeBettingMoney;
					} else {
						System.out.println("패자");
						this.tcpdata.lose ++;
						this.tcpdata.totalGame ++;
					}
					// DB와 게임 시작 시 프로필 리셋 
					
					// 베팅 초기화  
				
					
					
					gameRes_DBInsert(this.tcpdata); 
					
					
					
					try {
						this.tcpdata.last = false;
						this.tcpdata.callCount = 0;
						for (Integer z : tcpdata.test.get(tcpdata.UserPos).keySet()) {
							for (int i = 0; i < 7; i++) {
								
								ttt.get(z).get(i).setIcon(null);
							}
						}
						betting_Button_false ();
						//
						for (Map.Entry<Integer, String> entry : data.test.get(data.UserPos).entrySet()) {
							switch (entry.getKey()) {
							
							case 0:
							
									remove(Ingame_userProfile_panel_0);
					
									Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,entry.getValue());
									System.out.println(entry.getValue()+"," + Ingame_userProfile_panel_0.userMoney);
									add(Ingame_userProfile_panel_0);
								
								break;
							case 1:
								
									remove(Ingame_userProfile_panel_1);
									Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1,entry.getValue());
									System.out.println(entry.getValue()+"," + Ingame_userProfile_panel_1.userMoney);
									add(Ingame_userProfile_panel_1);
								
								break;
							case 2:
							
									remove(Ingame_userProfile_panel_2);
									Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2,entry.getValue());
									System.out.println(entry.getValue()+"," + Ingame_userProfile_panel_2.userMoney);
									add(Ingame_userProfile_panel_2);
								
								break;
							case 3:
							
									remove(Ingame_userProfile_panel_3);
									Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3,entry.getValue());
									add(Ingame_userProfile_panel_3);
								
								break;
							case 4:
							
									remove(Ingame_userProfile_panel_4);
									Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4,entry.getValue());
									add(Ingame_userProfile_panel_4);
									
								break;
							}
						}
						//
						turnNickname.setText("승: "+data.winner+"번 / "
	                            +jokbbo.jokbo(data.playerDeck.get(Integer.parseInt(data.winner))));
						repaint();
						
						Thread.sleep(3000);
						
						turnNickname.setText("차례 : ");
						
						p1_turn.setIcon(default_bet);
						p2_turn.setIcon(default_bet);
						p3_turn.setIcon(default_bet);
						p4_turn.setIcon(default_bet);
						p5_turn.setIcon(default_bet);
						p1_bet_jokbo.setText("베팅 : "+ 0);
						p2_bet_jokbo.setText("베팅 : "+ 0);
						p3_bet_jokbo.setText("베팅 : "+ 0);
						p4_bet_jokbo.setText("베팅 : "+ 0);
						p5_bet_jokbo.setText("베팅 : "+ 0);
						repaint();
						if(num==0) {
							JButton GameStart = new JButton("게임 시작");
							GameStart.setBounds(500, 280, 200, 80);
							add(GameStart);
							GameStart.addActionListener(new ActionListener() {
								//게임 시작 버튼
								@Override
								public void actionPerformed(ActionEvent e) {
									betting_Button_true();
									tcpdata.DataDestination = "Game";
									tcpdata.userCount = num;
									for (int i = 2; i < 15; i++) {
										for (int j = 1; j < 5; j++) {
											tcpdata.dealerDeck.add(new PokerCard(i,j));
										}
									}
									tcpdata.roomclose.replace(tcpdata.UserPos, true);
									GameProcess();
									game_panel.remove(GameStart);
									repaint();
								}
							});
							
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(tcpdata.callCount == tcpdata.test.get(tcpdata.UserPos).keySet().size()-1&&tcpdata.playerDeck.get(0).size()==7) {
					for (Integer z : data.test.get(tcpdata.UserPos).keySet()) {
						if(z.equals(num)) {
							ttt.get(z).get(tcpdata.playerDeck.get(z).size()-1).setIcon(data.playerDeck.get(z).get(tcpdata.playerDeck.get(z).size()-1).img);
						}else {
							ttt.get(z).get(tcpdata.playerDeck.get(z).size()-1).setIcon(cdback);							
						}
					}
				}else if(data.callCount == data.test.get(tcpdata.UserPos).keySet().size()-1) {
					bt.setEnabled(false);
					for (Integer z : data.test.get(tcpdata.UserPos).keySet()) {
						ttt.get(z).get(tcpdata.playerDeck.get(z).size()-1).setIcon(data.playerDeck.get(z).get(tcpdata.playerDeck.get(z).size()-1).img);
					}
					
					p1_turn.setIcon(default_bet);
					p2_turn.setIcon(default_bet);
					p3_turn.setIcon(default_bet);
					p4_turn.setIcon(default_bet);
					p5_turn.setIcon(default_bet);
					
				}

				break;	
			case "betting_ddadang" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				cht.append("전체 베팅금액 : "+data.wholeBettingMoney+ ", 배팅 머니 : "+ data.btMoney+", 내 머니 : "+data.money);
				playerturn();
				
				
				break;
			case "betting_half" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				cht.append("전체 베팅금액 : "+data.wholeBettingMoney+ ", 배팅 머니 : "+ data.btMoney+", 내 머니 : "+data.money);
				playerturn();
				
				
				break;

			case "betting_quater" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				cht.append("전체 베팅금액 : "+data.wholeBettingMoney+ ", 배팅 머니 : "+ data.btMoney+", 내 머니 : "+data.money);
				playerturn();
				
				
				break;
			case "betting_max" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);
				cht.append("전체 베팅금액 : "+data.wholeBettingMoney+ ", 배팅 머니 : "+ data.btMoney+", 내 머니 : "+data.money);
				playerturn();
				
				
				break;
			default :
				
				break;
			} 
			
			
		

	    	repaint();
	    
			break;

		}
		repaint();
		
	}
	void whatBetting(String whatBet, int go) {
		ImageIcon img;
		switch (whatBet) {
		case "call":
			img = new ImageIcon("img/gamepanel/call.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		case "bbing":
			img = new ImageIcon("img/gamepanel/bbing.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		case "ddadang":
			img = new ImageIcon("img/gamepanel/ddadang.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		case "half":
			img = new ImageIcon("img/gamepanel/half.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		case "die":
			img = new ImageIcon("img/gamepanel/die.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		case "quarter":
			img = new ImageIcon("img/gamepanel/quarter.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		case "max":
			img = new ImageIcon("img/gamepanel/max.png");
			if (go == 0) {
				p1_turn.setIcon(img);	
			}
			if (go == 1) {
				p2_turn.setIcon(img);	
			}
			if (go == 2) {
				p3_turn.setIcon(img);	
			}
			if (go == 3) {
				p4_turn.setIcon(img);	
			}
			if (go == 4) {
				p5_turn.setIcon(img);	
			}
			break;
		}
	
	}
	
	void oneSplit() {
		for (Integer i : tcpdata.test.get(tcpdata.UserPos).keySet()) {
			Random number = new Random();
			int b = number.nextInt(tcpdata.dealerDeck.size());
			tcpdata.playerDeck.get(i).add(tcpdata.dealerDeck.get(b));
			tcpdata.dealerDeck.remove(b);
		}
		
		ch.send(tcpdata);
	}
	void split() {
		
		
		tcpdata.wholeBettingMoney = tcpdata.test.get(tcpdata.UserPos).size()*panMoney; 
		tcpdata.panMoney = panMoney;
		for (int i = 0 ; i < tcpdata.test.get(tcpdata.UserPos).size() ; i++) {
			tcpdata.bettingMoney.add(0);			
		}
		
		for (Integer i : tcpdata.test.get(tcpdata.UserPos).keySet()) {
			tcpdata.playerDeck.put(i,new ArrayList<PokerCard>());
		}
		
		
		for (Integer i : tcpdata.test.get(tcpdata.UserPos).keySet()) {
			for (int j = 0 ; j < 3 ;  j++) {
				Random number = new Random();
				int a = number.nextInt(tcpdata.dealerDeck.size());
				tcpdata.playerDeck.get(i).add(tcpdata.dealerDeck.get(a));
				tcpdata.dealerDeck.remove(a);			
			}
		}
		
		
		tcpdata.DataDestination = "Game";
		tcpdata.msg = "카드 나와라";
		ch.send(tcpdata);
	}
	
	void GameProcess() {
		// 카드  7장 나눠주기
		split();
		// 0번째 턴
		
		
	}
	
	int next(int a) {
		int testnum = 0;
		for (Integer integer : tcpdata.test.get(tcpdata.UserPos).keySet()) {
			if(testnum<integer) {
				testnum=integer;
			}
		}
		for (Integer integer : tcpdata.test.get(tcpdata.UserPos).keySet()) {
			if((a+1)%(testnum+1) == integer) {
				return (a+1)%(testnum+1);
			}
		}
		return next(a+1);
	}
	void playerturn() {
        if (this.num == next(tcpdata.userCount)) {
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
    
    
    void gameRes_DBInsert (TCPData data){
    	new SignDB().resInsert(data);
    }
    //
    // 베팅한 사람의 프로필 리셋
    void betting_profileReset (TCPData data, int num) {
    	
		
			System.out.println("이사람");
			switch (num) {
			
			case 0: // 실험  지우지 않고  settext만 해도 바뀌는지 
				Ingame_userProfile_panel_0.userMoney = data.money;
				Ingame_userProfile_panel_0.userMoney_text.setText("보유머니 : " + data.money);
					// 같은 걸로 바뀜 datamoney

				break;
			case 1:// 아니면 지우고 나서 해야 바뀌는지 
				Ingame_userProfile_panel_1.userMoney = data.money;
				Ingame_userProfile_panel_1.userMoney_text.setText("보유머니 : " + data.money);
				
				break;
			case 2:
				
				Ingame_userProfile_panel_2.userMoney = data.money;
				Ingame_userProfile_panel_2.userMoney_text.setText("보유머니 : " + data.money);
			
				break;
			case 3:

				Ingame_userProfile_panel_3.userMoney = data.money;
				Ingame_userProfile_panel_3.userMoney_text.setText("보유머니 : " + data.money);
			
				break;
			case 4:
				Ingame_userProfile_panel_4.userMoney = data.money;
				Ingame_userProfile_panel_4.userMoney_text.setText("보유머니 : " + data.money);
			
				break;
			}
		

		
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

