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
import java.util.HashMap;
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
import net_p.GameData;
import net_p.MsgData;
import net_p.MyData;
import net_p.NetExecute;
import net_p.Receiver;
import net_p.RoomChk;
import net_p.TCPData;
import net_p.UserData;





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
	//TCPData tcpData;

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
	
	JButton GameStart;
	HashMap<String, Integer> game_users, real_users ;
	
	ArrayList<Ingame_userProfile_panel> userprofile = new ArrayList<Ingame_userProfile_panel>();
	Login_frame login_frame;
	Game_panel game_panel;
	PokerGameMain pokerGamemain;
	
	ArrayList<PlayerCard_Label> player1cardShow;
	ArrayList<PlayerCard_Label> player2cardShow;
	ArrayList<PlayerCard_Label> player3cardShow;
	ArrayList<PlayerCard_Label> player4cardShow;
	ArrayList<PlayerCard_Label> player5cardShow;
	ArrayList<ArrayList<PlayerCard_Label>> cardJip;
	
	Receiver ch;
	
	Ingame_userProfile_panel Ingame_userProfile_panel_0;
	Ingame_userProfile_panel Ingame_userProfile_panel_1;
	Ingame_userProfile_panel Ingame_userProfile_panel_2;
	Ingame_userProfile_panel Ingame_userProfile_panel_3;
	Ingame_userProfile_panel Ingame_userProfile_panel_4;
	
	ArrayList<String> ipjang = new ArrayList<String>();
	
	public ArrayList<Integer> bettingMoney = new ArrayList<Integer>();
//	public int panMoney;
//	public int wholeBettingMoney;
//	public String winner;
	ImageIcon default_bet = new ImageIcon("img/gamepanel/default.png");
	ImageIcon cdback  = new ImageIcon("img/card/CardBackimg.png");
	GameData me;
	TCPData tcpData = new TCPData();
	
	MyData myData;
	Boolean gameStart_Gen;
	public Game_panel(Login_frame login_frame,Receiver ch,MyData myData,Integer addr) {
		
		me = new GameData();
		
		
		this.myData = myData;
		myData.pos = addr;
		
	
		me.panMoney = 10;
		me.wholeBettingMoney = 0;
		gameStart_Gen = false;
		this.ch = ch;
		this.ch.game_panel = this;
		this.ch.lobby_panel = null;
		this.login_frame = login_frame;		
		game_panel = this;
		// 족보 흔적기관
		jokbbo = new Jokbo();
		jk = new JokBoTEST();
		setBounds(0, 0, 1200, 800);
		setBackground(new Color(32,56,48));
		setLayout(null);
		
		// 도움말 help
		help = new JButton("help");
		help.setBounds(1040, 0, 70, 30);
		help.setBackground(Color.white);
		help.addActionListener(this);
		add(help);
		
		// 나가기 exit
		exit = new JButton("exit");
		exit.setBounds(1110,0,70,30);
		exit.setBackground(Color.white);
		add(exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (e.getSource() == exit) {
					TCPData tcpdata = new TCPData();

					login_frame.add(new Lobby(login_frame,ch,myData));
					login_frame.remove(game_panel);
					login_frame.repaint();
				
					tcpdata.name = myData.nickName;
					tcpdata.DataDestination = "testMove";
					tcpdata.UserPos = myData.pos;
					tcpdata.oData = new UserData(addr,-1,myData.nickName,null);
					ch.send(tcpdata);
					
				
					
//					tcpdata.name = myData.nickName;
//					tcpdata.DataDestination = "out";
//					tcpdata.oData = i;
//					ch.send(tcpdata);
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
		
		cardJip = new ArrayList<ArrayList<PlayerCard_Label>>();

		

		
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
					TCPData tcpdata = new TCPData();
					if(me.userCount == null) {
						me.userCount = 0;
					}
					if(me.prebetMoney*2>myData.money){
						me.btMoney = myData.money;
					}else {
						me.btMoney = me.prebetMoney;						
					}
					me.prebetMoney = me.btMoney;
					me.wholeBettingMoney += me.btMoney;
					myData.money -= me.btMoney;
					me.money = myData.money;
					me.playerNum = myData.playerNum;
					
					System.out.println("패널의 myData: "+ myData.money);
//					me.callCount = me.playerNum;
					me.callCount++;
					me.userCount = myData.playerNum;
					
					if(me.callCount == me.game_users.size()-1 && me.playerDeck.get(myData.playerNum).size()==7) {
						me.last = true;
						me.result = new HashMap<Integer, String>(); // "풀하우스_1,1"
						for (Integer cd : me.game_users.values()) {
							if(cd != null) {
								me.result.put(cd, jokbbo.jokbo(me.playerDeck.get(cd)));
							}
						}
						
						me.winner = jk.resultWinner(me.result)+"";
						
						tcpdata.DataDestination = "betting_call";
						tcpdata.name = myData.nickName;
						tcpdata.UserPos = myData.pos;
						tcpdata.oData = me;
						ch.send(tcpdata);
					}else if(me.callCount == me.game_users.size()-1){
						
						oneSplit();
					}else{
						
						tcpdata.DataDestination = "betting_call";
						tcpdata.name = myData.nickName;
						tcpdata.UserPos = myData.pos;
						tcpdata.oData = me;
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
				TCPData tcpdata = new TCPData();
				
				me.callCount=0;
				me.userCount = myData.playerNum;
				me.btMoney += me.panMoney;				
				me.prebetMoney = me.btMoney; 
				me.wholeBettingMoney += me.btMoney;
				myData.money -= me.btMoney;
				me.money = myData.money;
				me.playerNum = myData.playerNum;
				
				tcpdata.DataDestination = "betting_bbing";
				tcpdata.UserPos = myData.pos;
				tcpdata.name = myData.nickName;
				tcpdata.oData = me;
				ch.send(tcpdata);
				
				
			}
		});
    	ImageIcon i2 = new ImageIcon("img/gamepanel/ddadang_bt.gif");
        bt2 = new BetBtn("",0,55); //따당
        bt2.setIcon(i2);
    	bt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TCPData tcpdata = new TCPData();
				me.callCount=0;
				me.userCount = myData.playerNum;

				
				if(me.prebetMoney*2>myData.money){
					me.btMoney = myData.money;
				}else {
					me.btMoney = me.prebetMoney*2;
				}
				me.prebetMoney = me.btMoney;
				me.wholeBettingMoney += me.btMoney;
				myData.money -= me.btMoney;
				me.money = myData.money;
				me.playerNum = myData.playerNum;
				
				tcpdata.DataDestination = "betting_ddadang";
				tcpdata.name = myData.nickName;
				tcpdata.UserPos = myData.pos;
				tcpdata.oData = me;
				ch.send(tcpdata);
				
			}
		});
    	ImageIcon i3 = new ImageIcon("img/gamepanel/half_bt.gif");
        bt3 = new BetBtn("",85,55);//하프
        bt3.setIcon(i3);
    	bt3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TCPData tcpdata = new TCPData();
				me.callCount=0;
				me.userCount = myData.playerNum;
		
				if(me.prebetMoney*2>myData.money){
					me.btMoney = myData.money;
				}else {
					me.btMoney = me.wholeBettingMoney/2; 
				}
				me.prebetMoney = me.btMoney;
				me.wholeBettingMoney += me.btMoney;
				myData.money -= me.btMoney;
				me.money = myData.money;
				me.playerNum = myData.playerNum;
				
				tcpdata.DataDestination = "betting_half";
				tcpdata.name = myData.nickName;
				tcpdata.UserPos = myData.pos;
				tcpdata.oData = me;
				ch.send(tcpdata);
				
			}
		});
    	ImageIcon i4 = new ImageIcon("img/gamepanel/die_bt.gif");
        bt4 = new BetBtn("",0,110);//다이
        bt4.setIcon(i4);
        bt4.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
        		TCPData tcpdata = new TCPData();
                
                
            	me.userCount = myData.playerNum;
            	
            	me.game_users.replace(myData.nickName,null);
            	me.playerNum = myData.playerNum;
            	tcpdata.UserPos = myData.pos;
            	tcpdata.DataDestination = "betting_die";
				tcpdata.name = myData.nickName;
				tcpdata.oData = me;
				ch.send(tcpdata);
            }
        });
        ImageIcon i5 = new ImageIcon("img/gamepanel/quarter_bt.gif");
        bt5 = new BetBtn("",85,110);//쿼터
        bt5.setIcon(i5);
        bt5.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
            	TCPData tcpdata = new TCPData();
            	me.callCount=0;
            	me.userCount = myData.playerNum;
		
				if(me.prebetMoney*2>myData.money){
					me.btMoney = myData.money;
				}else {
				 
					me.btMoney = me.wholeBettingMoney/4; 					
				}
				me.prebetMoney = me.btMoney;
		        me.wholeBettingMoney += me.btMoney;
		        myData.money -= me.btMoney;
		        me.money = myData.money;  
		        me.playerNum = myData.playerNum;
		        
		        tcpdata.DataDestination = "betting_quarter";
		        tcpdata.name = myData.nickName;
		    	tcpdata.UserPos = myData.pos;
		        tcpdata.oData = me;	
		        ch.send(tcpdata);
            }
        });
        ImageIcon i6 = new ImageIcon("img/gamepanel/max_bt.gif");
        bt6 = new BetBtn("",0,165);//맥스
        bt6.setIcon(i6);
    	bt6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				TCPData tcpdata = new TCPData();
				me.callCount = 0;
				me.userCount = myData.playerNum;
		
				me.btMoney = myData.money;
				me.prebetMoney = me.btMoney;
				me.wholeBettingMoney += me.btMoney;
				myData.money -= me.btMoney;
				me.money = myData.money;
				me.playerNum = myData.playerNum;
				
				tcpdata.DataDestination = "betting_max";
				tcpdata.UserPos = myData.pos;
		        tcpdata.name = myData.nickName;
		        tcpdata.oData = me;	
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
		Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0);
    	Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1);
    	Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2);
    	Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3);
    	Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4);
    	add(Ingame_userProfile_panel_0);
    	add(Ingame_userProfile_panel_1);
    	add(Ingame_userProfile_panel_2);
    	add(Ingame_userProfile_panel_3);
    	add(Ingame_userProfile_panel_4);
        
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
					
					TCPData tcpdata = new TCPData();
					tcpdata.UserPos = myData.pos;
					tcpdata.name = myData.nickName;
					
					tcpdata.DataDestination = "Chatting";
					tcpdata.oData = new MsgData(myData.nickName,chf.getText());
					ch.send(tcpdata);
					
					chf.setText("");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		betting_Button_false ();
		
		cardJip.add(player1cardShow);
		cardJip.add(player2cardShow);
		cardJip.add(player3cardShow);
		cardJip.add(player4cardShow);
		cardJip.add(player5cardShow);
		
		TCPData tcpdata = new TCPData();
		tcpdata.name = myData.nickName;
		tcpdata.UserPos = myData.pos;
		tcpdata.DataDestination = "testMove";
		tcpdata.oData = new UserData(-1,addr,myData.nickName, null);
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
		switch (data.DataDestination) {
		
		
		case "testMove":
			real_users = ((HashMap<Integer, HashMap<String, Integer>>)data.oData).get(myData.pos);
			
			myData.playerNum = real_users.get(myData.nickName);
			
			if(myData.playerNum ==0 && gameStart_Gen == false) {
				if(data.UserPos == myData.pos) {
					
					gameStart_Gen = true;
					//System.out.println("너는 0번이다.");
					
					GameStart = new JButton("게임 시작");
//				GameStart.setEnabled(false);
					GameStart.setBounds(500, 280, 200, 80);
					add(GameStart);	
					
					GameStart.addActionListener(new ActionListener() {
						//게임 시작 버튼
						@Override
						public void actionPerformed(ActionEvent e) {
							
							roomckh(true);
							me.game_users = (HashMap<String, Integer>) real_users.clone();
							betting_Button_true();
							gameStart_Gen = false;
							me.userCount = myData.playerNum;
							
							me.dealerDeck = new ArrayList<PokerCard>();
							for (int i = 2; i < 15; i++) {
								for (int j = 1; j < 5; j++) {
									
									me.dealerDeck.add(new PokerCard(i,j));
								}
							}
							
							
							game_panel.remove(GameStart);
							game_panel.repaint();
							
							GameProcess();
						}
					});
				}
				
			}

			
			endGame_profileReset();
			
//			if (myData.playerNum == 0 && real_users.size() > 1) {
//			GameStart.setEnabled(true);
//			} else {
//			GameStart.setEnabled(false);
//			}
			
			break;
			
	
		case "Chatting" :
			MsgData msg = (MsgData)data.oData;
			if (data.UserPos == myData.pos) {
				
				cht.append(msg.nickName + " : "+ msg.msg+"\n");
				cht.setCaretPosition(cht.getDocument().getLength());
				
				
			}
			break;
		}
		if (myData.pos.equals(data.UserPos)) {
			switch (data.DataDestination) {
				
				
			case "RoomClose" :
				
				
				
				break;
			
			case "out" :	
				
				int playerNum = (int)data.oData;
				System.out.println(playerNum+"번 나감 ");
				out_profileRemove(playerNum);
				
				break;
				
			case "카드 나와라":
				GameData gd = (GameData)data.oData;
				me = gd;
				
				
				exit.setEnabled(false);
				
				for (Map.Entry<String, Integer> cd: me.game_users.entrySet()) {
					for (Integer i = 2; i >= 0; i--) {
						if((!cd.getValue().equals(myData.playerNum))&& i<2) {
							cardJip.get(cd.getValue()).get(i).setIcon(cdback);														
						}else {
							cardJip.get(cd.getValue()).get(i).setIcon(me.playerDeck.get(cd.getValue()).get(i).img);							
						}
					}
				}
				
				bt.setEnabled(false);
	
				break;	
				
			case "betting_bbing" :	// 이제 베팅 누가 했는지  보고 신호 줘야
				gd = (GameData)data.oData;
				me = gd;
				
				betting_Show(me.playerNum, "betting_bbing");
				betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
				playerturn();
				bt1.setEnabled(false);
				break;	
				
			case "betting_ddadang" :
				gd = (GameData)data.oData;
				me = gd;
				
				betting_Show(me.playerNum, "betting_ddadang");
				betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
				playerturn();
				bt1.setEnabled(false);
				break;
				
			case "betting_half" :
				gd = (GameData)data.oData;
				me = gd;
				
				betting_Show(me.playerNum, "betting_half");
				betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
				playerturn();
				bt1.setEnabled(false);
				break;	
			
			case "betting_quarter" :
				gd = (GameData)data.oData;
				me = gd;
				
				betting_Show(me.playerNum, "betting_quarter");
				betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
				playerturn();
				bt1.setEnabled(false);
				break;
				
			case "betting_max" :
				gd = (GameData)data.oData;
				me = gd;
				
				betting_Show(me.playerNum, "betting_max");
				betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
				playerturn();	
				bt1.setEnabled(false);
				break;
				
			case "betting_die":
				gd = (GameData)data.oData;
				me = gd;
				
				if(diechk() == 1) {
					System.out.println("종료입니다.");
					
					turnDefault();
					p1_turn.setIcon(default_bet);
					p2_turn.setIcon(default_bet);
					p3_turn.setIcon(default_bet);
					p4_turn.setIcon(default_bet);
					p5_turn.setIcon(default_bet);
					
					if (myData.nickName.equals(winnerName())) {
						System.out.println("승자");
						myData.win ++;
						myData.totalGame ++;
						myData.money += me.wholeBettingMoney;
						
					} else {
						System.out.println("패자");
						myData.lose ++;
						myData.totalGame ++;
					
					}
	
	
					
					
					gameRes_DBInsert(); 
					
					
					//여기
					try {
						me.last = false;
						me.callCount = 0;
	//					for (Map.Entry<String, Integer> z : me.game_users.entrySet()) {
							for (int i = 0; i < 7; i++) {
								
								cardJip.get(0).get(i).setIcon(null);
								cardJip.get(1).get(i).setIcon(null);
								cardJip.get(2).get(i).setIcon(null);
								cardJip.get(3).get(i).setIcon(null);
								cardJip.get(4).get(i).setIcon(null);
							}
	//					}
						betting_Button_false ();
						
						turnNickname.setText("승: "+winnerName());
						repaint();
						
						Thread.sleep(3000);
						exit.setEnabled(true);
						roomckh(false);
						endGame_profileReset();
						
						turnNickname.setText("차례 : ");
						betting.setText("전체 베팅금액: "+0+ ", 현재 판돈: "+ 0);
						p1_bet_jokbo.setText("베팅 : "+ 0);
						p2_bet_jokbo.setText("베팅 : "+ 0);
						p3_bet_jokbo.setText("베팅 : "+ 0);
						p4_bet_jokbo.setText("베팅 : "+ 0);
						p5_bet_jokbo.setText("베팅 : "+ 0);
						
						repaint();
						
						if(myData.playerNum == 0 && gameStart_Gen == false) {
							GameStart = new JButton("게임 시작");
							GameStart.setBounds(500, 280, 200, 80);
	//						GameStart.setEnabled(false);
							add(GameStart);
							gameStart_Gen = true;
							GameStart.addActionListener(new ActionListener() {
								//게임 시작 버튼
								@Override
								public void actionPerformed(ActionEvent e) {
									
									me.game_users = (HashMap<String, Integer>) real_users.clone();
									roomckh(true);
									betting_Button_true();
									gameStart_Gen = false;
									me.userCount = myData.playerNum;
									
									me.dealerDeck = new ArrayList<PokerCard>();
									for (int i = 2; i < 15; i++) {
										for (int j = 1; j < 5; j++) {
											
											me.dealerDeck.add(new PokerCard(i,j));
										}
									}
								
									
									game_panel.remove(GameStart);
									game_panel.repaint();
									
									GameProcess();
								}
							});
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}else {
					
					betting_Show(me.playerNum, "betting_die");
					playerturn();
				}
	
				break;
				
			case "betting_call":
				gd = (GameData)data.oData;
				me = gd;
				
				betting_Show(me.playerNum, "betting_call");
				betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
				playerturn();	
				bt1.setEnabled(false);
				System.out.println(me.callCount+"=========================");
				System.out.println(me.game_users.keySet().size()-1);
				if((me.callCount == me.game_users.keySet().size()-1 && me.playerDeck.get(myData.playerNum).size()==7 && me.last)||
					(me.callCount == me.game_users.keySet().size()-1 && me.playerDeck.get(1).size()==7 && me.last)
						) {
					System.out.println("종료입니다.");
					turnDefault();
					// 승패 입력
					if (myData.playerNum == Integer.parseInt(me.winner)) {
						System.out.println("승자");
						myData.win ++;
						myData.totalGame ++;
						myData.money += me.wholeBettingMoney;
						
					} else {
						System.out.println("패자");
						myData.lose ++;
						myData.totalGame ++;
					
					}
	
	
					
					
					gameRes_DBInsert(); 
					
					
					
					try {
						me.last = false;
						me.callCount = 0;
						for (Map.Entry<String, Integer> z : me.game_users.entrySet()) {
							for (int i = 0; i < 7; i++) {
								cardJip.get(0).get(i).setIcon(null);
								cardJip.get(1).get(i).setIcon(null);
								cardJip.get(2).get(i).setIcon(null);
								cardJip.get(3).get(i).setIcon(null);
								cardJip.get(4).get(i).setIcon(null);
							}
						}
						betting_Button_false ();
						
						turnNickname.setText("승: "+me.winner+"번 / "+jokbbo.jokbo(me.playerDeck.get(Integer.parseInt(me.winner))));
						repaint();
	
						Thread.sleep(3000);
						roomckh(false);
						if (myData.money == 0) {
	                        TCPData tcpdata = new TCPData();
	
	                        login_frame.add(new Lobby(login_frame,ch,myData));
	                        login_frame.remove(game_panel);
	                        login_frame.repaint();
	                    	tcpdata.UserPos = myData.pos;
	                        tcpdata.name = myData.nickName;
	                        tcpdata.DataDestination = "testMove";
	                        tcpdata.oData = new UserData(myData.pos,-1,myData.nickName,null);
	                        ch.send(tcpdata);
	                    }
						endGame_profileReset();
						
						turnNickname.setText("차례 : ");
						betting.setText("전체 베팅금액: "+0+ ", 현재 판돈: "+ 0);
						p1_bet_jokbo.setText("베팅 : "+ 0);
						p2_bet_jokbo.setText("베팅 : "+ 0);
						p3_bet_jokbo.setText("베팅 : "+ 0);
						p4_bet_jokbo.setText("베팅 : "+ 0);
						p5_bet_jokbo.setText("베팅 : "+ 0);
						exit.setEnabled(true);
						repaint();
						
						if(myData.playerNum == 0 && gameStart_Gen == false) {
							GameStart = new JButton("게임 시작");
							GameStart.setBounds(500, 280, 200, 80);
	//						GameStart.setEnabled(false);
							add(GameStart);
							gameStart_Gen = true;
							GameStart.addActionListener(new ActionListener() {
								//게임 시작 버튼
								@Override
								public void actionPerformed(ActionEvent e) {
									
									me.game_users = (HashMap<String, Integer>) real_users.clone();
									betting_Button_true();
									gameStart_Gen = false;
									me.userCount = myData.playerNum;
	
									roomckh(true);
									me.dealerDeck = new ArrayList<PokerCard>();
									for (int i = 2; i < 15; i++) {
										for (int j = 1; j < 5; j++) {
											
											me.dealerDeck.add(new PokerCard(i,j));
										}
									}
								
									
									game_panel.remove(GameStart);
									game_panel.repaint();
									
									GameProcess();
								}
							});
							
						}
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else if(me.callCount == me.game_users.keySet().size()-1 && me.playerDeck.get(0).size()==7) {
					me.callCount=0;
					if(myData.money <= 0) {
						playerturn();						
					}else {
						playerturn();
						bt.setEnabled(false);
					}
					
					for (Map.Entry<String, Integer > z :me.game_users.entrySet()) {
						if(me.game_users.get(z.getKey()) != null) {
							if(z.getValue().equals(myData.playerNum)) {
//								cardJip.get(z.getValue())
//								.get(me.playerDeck.get(z.getValue()).size()-1)
//								.setIcon(me.playerDeck.get(z.getValue())
//								.get(me.playerDeck.get(z.getValue()).size()-1).img);
	
								cardJip.get(z.getValue()).get(me.playerDeck.get(z.getValue()).size()-1).setIcon(me.playerDeck.get(z.getValue()).get(me.playerDeck.get(z.getValue()).size()-1).img);
							}else {
								cardJip.get(z.getValue()).get(me.playerDeck.get(z.getValue()).size()-1).setIcon(cdback);							
							}
						}
					}
					turnDefault();
				}else if(me.callCount == me.game_users.keySet().size()-1) {
					me.callCount=0;
					if(myData.money <= 0) {
						playerturn();						
					}else {
						playerturn();
						bt.setEnabled(false);
					}
					for (Map.Entry<String, Integer > turn : me.game_users.entrySet()) {
						
						if(me.game_users.get(turn.getKey()) != null) {
//							cardJip.get(turn.getValue())
//							.get(me.playerDeck.get(turn.getValue()).size()-1)
//							.setIcon(me.playerDeck.get(turn.getValue())
//							.get(me.playerDeck.get(turn.getValue()).size()-1).img);
							
							cardJip.get(turn.getValue()).get(me.playerDeck.get(turn.getValue()).size()-1).setIcon(me.playerDeck.get(turn.getValue()).get(me.playerDeck.get(turn.getValue()).size()-1).img);
						}
					}
					
					turnDefault();
					
				}
	
				break;		
	
			}
		}
		repaint();

		
	}
	
	void roomckh(boolean chk) {
		TCPData roomchk = new TCPData();
		roomchk.oData = new RoomChk(myData.pos,chk);
		roomchk.UserPos = myData.pos;
		roomchk.DataDestination = "RoomChk";
		ch.send(roomchk);
	}

	
	void GameProcess() {
		// 카드  7장 나눠주기
		first_split();
		// 0번째 턴
	
	}

	void first_split() {
		// 처음 입장판돈 
		me.wholeBettingMoney = me.game_users.size()*me.panMoney; 
		me.playerDeck = new HashMap<Integer, ArrayList<PokerCard>>();
		// 플레이어 카드집 공간 생성
		for (Map.Entry<String, Integer > cd : me.game_users.entrySet()) {
			me.playerDeck.put(cd.getValue(),new ArrayList<PokerCard>());
		}
		
		// 카드 세 장씩 나누기
		for (Map.Entry<String, Integer > cd : me.game_users.entrySet()) {
		
			for (int j = 0 ; j < 3 ;  j++) {
				Random number = new Random();
				int rd_cardNum = number.nextInt(me.dealerDeck.size());
				me.playerDeck.get(cd.getValue()).add(me.dealerDeck.get(rd_cardNum));
				me.dealerDeck.remove(rd_cardNum);	
				
			}
			
		}
		
		TCPData tcpdata = new TCPData();
		tcpdata.name = myData.nickName;
		tcpdata.DataDestination = "카드 나와라";
		tcpdata.UserPos = myData.pos;
		me.prebetMoney = 1;
		tcpdata.oData = me;
		
		
		ch.send(tcpdata);
	}
	
	void oneSplit() {
		for (Map.Entry<String, Integer > cd : me.game_users.entrySet()) {
		
		
			if(me.game_users.get(cd.getKey()) != null) {
				Random number = new Random();
				int b = number.nextInt(me.dealerDeck.size());
				me.playerDeck.get(cd.getValue()).add(me.dealerDeck.get(b));
				me.dealerDeck.remove(b);
			}
		}
		TCPData tcpdata = new TCPData();
		tcpdata.name = myData.nickName;
		tcpdata.DataDestination = "betting_call";
		tcpdata.UserPos = myData.pos;
		tcpdata.oData = me;
		ch.send(tcpdata);
	}

	

	
	int next(int a) {
		int testnum = 0;
		for (Map.Entry<String, Integer > jump : me.game_users.entrySet()) {
			
			if(me.game_users.get(jump.getKey()) != null) {
				if(testnum<jump.getValue()) {
					testnum=jump.getValue();
				}
			}
		}
		for (Map.Entry<String, Integer > jump : me.game_users.entrySet()) {
		
			if(me.game_users.get(jump.getKey()) != null) {
				if((a+1)%(testnum+1) == jump.getValue()) {
					return (a+1)%(testnum+1);
				}
			}
		}
		return next(a+1);
	}
	
	int diechk() {
		int alive = 0;
		for (Map.Entry<String, Integer > jump : me.game_users.entrySet()) {
			if(me.game_users.get(jump.getKey()) != null) {
				alive++;
			}
		}
		System.out.println(alive+"--------------------die");
		return alive;
	}
	String winnerName() {
		String wname = "";
		for (Map.Entry<String, Integer > jump : me.game_users.entrySet()) {
			if(me.game_users.get(jump.getKey()) != null) {
				wname = jump.getKey();
			}
		}
		return wname;
	}

	
	void playerturn() {

		System.out.println(myData.playerNum +","+me.userCount);
        if (myData.playerNum == next(me.userCount)) {
        	System.out.println("내 차례");
        	if(myData.money <= 0) {
        		betting_Button_false();
        		bt.setEnabled(true);
        	}else {
        		betting_Button_true();  
        		
        	}
        } else {
        	System.out.println(" 차례");
            betting_Button_false();
        }
    }
	
	void turnDefault(){

		p1_turn.setIcon(default_bet);
		p2_turn.setIcon(default_bet);
		p3_turn.setIcon(default_bet);
		p4_turn.setIcon(default_bet);
		p5_turn.setIcon(default_bet);
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
    
    
    void gameRes_DBInsert (){
    	new SignDB().resInsert(myData);
    }
    //
    // 베팅한 사람의 프로필 리셋
    void betting_Show (Integer playerNum, String whatBet) {
    	
    	
			switch(playerNum) {
			case 0:
				p1_bet_jokbo.setText("베팅 : "+ me.btMoney);
				betting_profileReset (0);
				whatBetting(whatBet,playerNum);
				break;
			case 1:
				p2_bet_jokbo.setText("베팅 : "+ me.btMoney);
				betting_profileReset (1);
				whatBetting(whatBet,playerNum);
				break;
			case 2:
				p3_bet_jokbo.setText("베팅 : "+ me.btMoney);
				betting_profileReset (2);
				whatBetting(whatBet,playerNum);
				break;
			case 3:
				p4_bet_jokbo.setText("베팅 : "+ me.btMoney);
				betting_profileReset (3);
				whatBetting(whatBet,playerNum);
				break;
			case 4:
				p5_bet_jokbo.setText("베팅 : "+ me.btMoney);
				betting_profileReset (4);
				whatBetting(whatBet,playerNum);
				break;
			}
			
		
			
	}
    
    
    void betting_profileReset (Integer playerNum) {
    	
		
			System.out.println("이사람");
			switch (playerNum) {
			
			case 0:
				Ingame_userProfile_panel_0.userMoney = me.money;
				Ingame_userProfile_panel_0.userMoney_text.setText("보유머니 : " + me.money);
					

				break;
			case 1:
				Ingame_userProfile_panel_1.userMoney = me.money;
				Ingame_userProfile_panel_1.userMoney_text.setText("보유머니 : " + me.money);
				
				break;
			case 2:
				
				Ingame_userProfile_panel_2.userMoney = me.money;
				Ingame_userProfile_panel_2.userMoney_text.setText("보유머니 : " + me.money);
			
				break;
			case 3:

				Ingame_userProfile_panel_3.userMoney = me.money;
				Ingame_userProfile_panel_3.userMoney_text.setText("보유머니 : " + me.money);
			
				break;
			case 4:
				Ingame_userProfile_panel_4.userMoney = me.money;
				Ingame_userProfile_panel_4.userMoney_text.setText("보유머니 : " + me.money);
			
				break;
			}
		

		
    }
    
    void ipjang_profileReset () {

		for (Map.Entry<String, Integer> entry : real_users.entrySet()) {
				
				switch (entry.getValue()) {
				
				case 0:
					if (Ingame_userProfile_panel_0 == null) {
						Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,entry.getKey());
						add(Ingame_userProfile_panel_0);
					}
					
					break;
				case 1:
					if (Ingame_userProfile_panel_1 == null) {
						Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1,entry.getKey());
						add(Ingame_userProfile_panel_1);
					}
					
					break;
				case 2:
					if (Ingame_userProfile_panel_2 == null) {
						Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2,entry.getKey());
						add(Ingame_userProfile_panel_2);
					}
					
					break;
				case 3:
					if (Ingame_userProfile_panel_3 == null) {
						Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3,entry.getKey());
						add(Ingame_userProfile_panel_3);
					}
					
					break;
				case 4:
					if (Ingame_userProfile_panel_4 == null) {
						Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4,entry.getKey());
						add(Ingame_userProfile_panel_4);
					}	
					
					break;
					
				}
		}
    }
	

    
    void whatBetting(String whatBet, Integer playerNum) {
		ImageIcon img;
		
		switch (whatBet) {
		
		case "betting_call":
			img = new ImageIcon("img/gamepanel/call.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		case "betting_bbing":
			img = new ImageIcon("img/gamepanel/bbing.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		case "betting_ddadang":
			img = new ImageIcon("img/gamepanel/ddadang.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		case "betting_half":
			img = new ImageIcon("img/gamepanel/half.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		case "betting_die":
			img = new ImageIcon("img/gamepanel/die.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		case "quarter":
			img = new ImageIcon("img/gamepanel/quarter.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		case "betting_max":
			img = new ImageIcon("img/gamepanel/max.png");
			if (playerNum.equals(0)) {
				p1_turn.setIcon(img);	
			}
			if (playerNum.equals(1)) {
				p2_turn.setIcon(img);	
			}
			if (playerNum.equals(2)) {
				p3_turn.setIcon(img);	
			}
			if (playerNum.equals(3)) {
				p4_turn.setIcon(img);	
			}
			if (playerNum.equals(4)) {
				p5_turn.setIcon(img);	
			}
			break;
		}
	
	}
    
    void out_profileRemove (int playerNum) {
    	
			switch (playerNum) {
				
				case 0:
					
					remove(Ingame_userProfile_panel_0);
					
					break;
				case 1:
					
					remove(Ingame_userProfile_panel_1);
					
					break;
				case 2:
					
					remove(Ingame_userProfile_panel_2);
					
					break;
				case 3:
				
					remove(Ingame_userProfile_panel_3);
					
					break;
				case 4:
					
					remove(Ingame_userProfile_panel_4);
					
					break;
					
			}
	}
    
    void endGame_profileReset() {
    	
    	boolean player_profile_reset [] = {false,false,false,false,false};
    	for (Map.Entry<String, Integer> entry : real_users.entrySet()) {
			System.out.println(entry.getKey()+ "의 프로필 생성");
			switch (entry.getValue()) {
			
			case 0:
				player_profile_reset[0] = true;
				remove(Ingame_userProfile_panel_0);
				Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,entry.getKey());
				add(Ingame_userProfile_panel_0);
				
				break;
			case 1:
				player_profile_reset[1] = true;
				remove(Ingame_userProfile_panel_1);
				Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1,entry.getKey());
				add(Ingame_userProfile_panel_1);
				
				break;
			case 2:
				player_profile_reset[2] = true;
				remove(Ingame_userProfile_panel_2);
				Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2,entry.getKey());
				add(Ingame_userProfile_panel_2);
				
				break;
			case 3:
				player_profile_reset[3] = true;;
				remove(Ingame_userProfile_panel_3);
				Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3,entry.getKey());
				add(Ingame_userProfile_panel_3);
				
				break;
			case 4:
				player_profile_reset[4] = true;
				remove(Ingame_userProfile_panel_4);
				Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4,entry.getKey());
				add(Ingame_userProfile_panel_4);
				
				break;
				
			}
    	}
    	for (int i = 0 ; i < 	player_profile_reset.length ; i++) {
    		if (player_profile_reset[i]) {
    			
    		} else {
    			switch (i) {
    			
    			case 0:
    				
    				remove(Ingame_userProfile_panel_0);
    				Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0);
    				add(Ingame_userProfile_panel_0);
    				
    				break;
    			case 1:
    				
    				remove(Ingame_userProfile_panel_1);
    				Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1);
    				add(Ingame_userProfile_panel_1);
    				
    				break;
    			case 2:
    				
    				remove(Ingame_userProfile_panel_2);
    				Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2);
    				add(Ingame_userProfile_panel_2);
    				
    				break;
    			case 3:
    		
    				remove(Ingame_userProfile_panel_3);
    				Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3);
    				add(Ingame_userProfile_panel_3);
    				
    				break;
    			case 4:
    				
    				remove(Ingame_userProfile_panel_4);
    				Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4);
    				add(Ingame_userProfile_panel_4);
    				
    				break;
    			}
    		}
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

