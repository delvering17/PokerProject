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
					tcpdata.name = myData.nickName;
					tcpdata.DataDestination = "testMove";
					tcpdata.oData = new UserData(addr,-1,myData.nickName,null);
					ch.send(tcpdata);
					
					login_frame.add(new Lobby(login_frame,ch,myData));
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
					
					if(me.prebetMoney*2>myData.money){
						me.btMoney = myData.money;
					}else {
						me.btMoney = me.prebetMoney;						
					}
					me.prebetMoney = me.btMoney;
					me.wholeBettingMoney += me.btMoney;
					myData.money -= me.btMoney;
					System.out.println("패널의 myData: "+ myData.money);
//					me.callCount = me.playerNum;
					me.callCount++;
					me.userCount = myData.playerNum;
					
					if(me.callCount == me.game_users.size()-1 && me.playerDeck.get(myData.playerNum).size()==7) {
						me.result = new HashMap<Integer, String>();
						for (Integer cd : me.game_users.values()) {
						
							me.result.put(cd, jokbbo.jokbo(me.playerDeck.get(cd)));
						}
						
						me.winner = jk.resultWinner(me.result)+"";
						me.last = true;
						
						tcpdata.DataDestination = "betting_call";
						tcpdata.name = myData.nickName;
						tcpdata.oData = me;
						ch.send(tcpdata);
					}else if(me.callCount == me.game_users.size()-1){
						
						oneSplit();
					}else{
						
						tcpdata.DataDestination = "betting_call";
						tcpdata.name = myData.nickName;
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
				me.btMoney = me.panMoney;				
				me.prebetMoney = me.btMoney; 
				me.wholeBettingMoney += me.btMoney;
				myData.money -= me.btMoney;
					
				tcpdata.DataDestination = "betting_bbing";
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
				
				tcpdata.DataDestination = "betting_ddadang";
				tcpdata.name = myData.nickName;
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
			
				
				tcpdata.DataDestination = "betting_half";
				tcpdata.name = myData.nickName;
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
		          
		        tcpdata.DataDestination = "betting_quarter";
		        tcpdata.name = myData.nickName;
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
				me.wholeBettingMoney += me.btMoney ;
				myData.money -= me.btMoney;
				
				
				
				tcpdata.DataDestination = "betting_max";
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
		System.out.println("게임 패널  들어오냐");
		switch (data.DataDestination) {
		
		
		case "testMove":
			System.out.println("여기까지 오냐 게임 패널의 테스트 무브? ");
			real_users = ((HashMap<Integer, HashMap<String, Integer>>)data.oData).get(myData.pos);
			
			myData.playerNum = real_users.get(myData.nickName);
			//System.out.println(myData.playerNum+ "번으로 접속 ");
			
			if(myData.playerNum ==0 && gameStart_Gen == false) {
			
				gameStart_Gen = true;
			//System.out.println("너는 0번이다.");
			
			JButton GameStart = new JButton("게임 시작");
			GameStart.setBounds(500, 280, 200, 80);
			add(GameStart);
			
			GameStart.addActionListener(new ActionListener() {
				//게임 시작 버튼
				@Override
				public void actionPerformed(ActionEvent e) {
					
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
			
			break;
			
		case "Chatting" :
			MsgData msg = (MsgData)data.oData;
			
			cht.append(msg.nickName + " : "+ msg.msg+"\n");
			cht.setCaretPosition(cht.getDocument().getLength());
			
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
			System.out.println("삥");
			
			betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
			cht.append("전체 베팅금액: "+me.wholeBettingMoney+ ", 배팅 머니: "+ me.btMoney);
			playerturn();
			
			break;	
			
		case "betting_ddadang" :
			gd = (GameData)data.oData;
			me = gd;
			System.out.println("따당");
			
			betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
			cht.append("전체 베팅금액 : "+me.wholeBettingMoney+ ", 배팅 머니 : "+ me.btMoney+", 내 머니 : "+myData.money);
			playerturn();
			
			break;
			
		case "betting_half" :
			gd = (GameData)data.oData;
			me = gd;
			System.out.println("하프");
			
		betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
		cht.append("전체 베팅금액 : "+me.wholeBettingMoney+ ", 배팅 머니 : "+ me.btMoney+", 내 머니 : "+myData.money);
		playerturn();
			
			break;	
		
		case "betting_quarter" :
			gd = (GameData)data.oData;
			me = gd;
			System.out.println("쿼터");
			
			betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
			cht.append("전체 베팅금액 : "+me.wholeBettingMoney+ ", 배팅 머니 : "+ me.btMoney+", 내 머니 : "+myData.money);
			playerturn();
		
			break;
			
		case "betting_max" :
			gd = (GameData)data.oData;
			me = gd;
			System.out.println("쿼터");
			
			betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
			cht.append("전체 베팅금액 : "+me.wholeBettingMoney+ ", 배팅 머니 : "+ me.btMoney+", 내 머니 : "+myData.money);
			playerturn();	
			
			break;
			
		case "betting_die":
			gd = (GameData)data.oData;
			me = gd;
			System.out.println("다이");
			
			playerturn();
			
			break;
			
		case "betting_call":
			gd = (GameData)data.oData;
			me = gd;
			System.out.println("콜");
			
			betting.setText("전체 베팅금액: "+me.wholeBettingMoney+ ", 현재 판돈: "+ me.panMoney);
			cht.append("전체 베팅금액 : "+me.wholeBettingMoney+ ", 배팅 머니 : "+ me.btMoney+", 내 머니 : "+myData.money);
			playerturn();	
			System.out.println("익스큐트의 myData: "+ myData.money);
			if(me.callCount == me.game_users.keySet().size()-1 && me.playerDeck.get(0).size()==7 && me.last) {
				System.out.println("종료입니다.");
				System.out.println("집가자 가고싶어요 보내줘요" + me.winner);
				
				// DB 입력 
			
				
				
				//
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
				// DB와 게임 시작 시 프로필 리셋 
				
				// 베팅 초기화  
			
				
				
//				gameRes_DBInsert(this.tcpData); 
				
				
				
				try {
					me.last = false;
					me.callCount = 0;
					for (Map.Entry<String, Integer> z : me.game_users.entrySet()) {
						for (int i = 0; i < 7; i++) {
							
							cardJip.get(z.getValue()).get(i).setIcon(null);
						}
					}
					betting_Button_false ();
					//
					
					//
					turnNickname.setText("승: "+me.winner+"번 / "+jokbbo.jokbo(me.playerDeck.get(Integer.parseInt(me.winner))));
					repaint();
					
					Thread.sleep(3000);
					
//					for (Map.Entry<String, Integer > entry : me.game_users.entrySet()) {
//						switch (entry.getValue()) {
//						
//						case 0:
//						
//								remove(Ingame_userProfile_panel_0);
//				
//								Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,entry.getKey());
//								System.out.println(entry.getValue()+"," + Ingame_userProfile_panel_0.userMoney);
//								add(Ingame_userProfile_panel_0);
//							
//							break;
//						case 1:
//							
//								remove(Ingame_userProfile_panel_1);
//								Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1,entry.getKey());
//								System.out.println(entry.getValue()+"," + Ingame_userProfile_panel_1.userMoney);
//								add(Ingame_userProfile_panel_1);
//							
//							break;
//						case 2:
//						
//								remove(Ingame_userProfile_panel_2);
//								Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2,entry.getKey());
//								System.out.println(entry.getValue()+"," + Ingame_userProfile_panel_2.userMoney);
//								add(Ingame_userProfile_panel_2);
//							
//							break;
//						case 3:
//						
//								remove(Ingame_userProfile_panel_3);
//								Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3,entry.getKey());
//								add(Ingame_userProfile_panel_3);
//							
//							break;
//						case 4:
//						
//								remove(Ingame_userProfile_panel_4);
//								Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4,entry.getKey());
//								add(Ingame_userProfile_panel_4);
//								
//							break;
//						}
//					}
					
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
					
					if(me.playerNum == 0) {
						JButton GameStart = new JButton("게임 시작");
						GameStart.setBounds(500, 280, 200, 80);
						add(GameStart);
						GameStart.addActionListener(new ActionListener() {
							//게임 시작 버튼
							@Override
							public void actionPerformed(ActionEvent e) {
								
								me.game_users = (HashMap<String, Integer>) real_users.clone();
								betting_Button_true();
								
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
						if(z.getValue().equals(me.playerNum)) {
							cardJip.get(z.getValue()).get(me.playerDeck.get(z.getValue()).size()-1).setIcon(me.playerDeck.get(z.getValue()).get(me.playerDeck.get(z.getValue()).size()-1).img);
						}else {
							cardJip.get(z.getValue()).get(me.playerDeck.get(z.getValue()).size()-1).setIcon(cdback);							
						}
					}
				}
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
						cardJip.get(turn.getValue()).get(me.playerDeck.get(turn.getValue()).size()-1).setIcon(gd.playerDeck.get(turn.getValue()).get(me.playerDeck.get(turn.getValue()).size()-1).img);
					}
				}
				
				p1_turn.setIcon(default_bet);
				p2_turn.setIcon(default_bet);
				p3_turn.setIcon(default_bet);
				p4_turn.setIcon(default_bet);
				p5_turn.setIcon(default_bet);
				
			}

			break;		
			
			
//		this.tcpData.playData = data.playData;
//		this.tcpData.playerDeck = data.playerDeck;
//		this.tcpData.easyStudy = data.easyStudy;
//		this.tcpData.userName = data.userName;
//		this.tcpData.test = data.test;
//		if(data.UserPos==this.tcpData.UserPos) {
//			this.tcpData.dealerDeck = data.dealerDeck;
//			cht.append(data.name+" : "+data.msg+"\n");
//			this.tcpData.prebetMoney = data.prebetMoney;
//			this.tcpData.userCount = data.userCount;
//			this.tcpData.callCount = data.callCount;
//			this.tcpData.last = data.last;
//			this.tcpData.userNumber = data.userNumber;
//		}
		
		
	
//		
//		


//
//		if (data.msg.contains("[입장]") ) {
//			System.out.println("프로필 생성");
//			
////			for () {
////				
////				new Ingame_userProfile_panel(); // playNum, nickname
////				
////			}
//			for (Map.Entry<String, Integer > entry : data.test.get(data.UserPos).entrySet()) {
//				switch (entry.getValue()) {
//				
//				case 0:
//					if (Ingame_userProfile_panel_0 == null) {
//						Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,entry.getKey());
//						add(Ingame_userProfile_panel_0);
//					}
//					break;
//				case 1:
//					if (Ingame_userProfile_panel_1 == null) {
//						Ingame_userProfile_panel_1 = new Ingame_userProfile_panel(1,entry.getKey());
//						add(Ingame_userProfile_panel_1);
//					}
//					break;
//				case 2:
//					if (Ingame_userProfile_panel_2 == null) {
//						Ingame_userProfile_panel_2 = new Ingame_userProfile_panel(2,entry.getKey());
//						add(Ingame_userProfile_panel_2);
//					}
//					break;
//				case 3:
//					if (Ingame_userProfile_panel_3 == null) {
//						Ingame_userProfile_panel_3 = new Ingame_userProfile_panel(3,entry.getKey());
//						add(Ingame_userProfile_panel_3);
//					}
//					break;
//				case 4:
//					if (Ingame_userProfile_panel_4 == null) {
//						Ingame_userProfile_panel_4 = new Ingame_userProfile_panel(4,entry.getKey());
//						add(Ingame_userProfile_panel_4);
//					}	
//					break;
//				}
//			}
//
////			if (Ingame_userProfile_panel_0 == null) {
////				Ingame_userProfile_panel_0 = new Ingame_userProfile_panel(0,);
////			}
//			
//	
//		}
		//
	
		
//			

			
		
//		case "Game":
//			GameData gd = (GameData)data.oData;
//			
//			int go = 0;
//			if (gd.msg.contains("betting")) {
//				
//				
////				for (Map.Entry< String, Integer> entry : tdata.test.get(data.UserPos).entrySet()) {
////					if (data.name.equals(entry.getValue())) {
////						go = entry.getValue();
////					}
////				}
//				switch(go) {
//				case 0:
//					p1_bet_jokbo.setText("베팅 : "+ gd.btMoney);
//					betting_profileReset (gd, 0);
//					whatBetting(gd.msg.split("_")[1],go);
//					break;
//				case 1:
//					p2_bet_jokbo.setText("베팅 : "+ gd.btMoney);
//					betting_profileReset (gd, 1);
//					whatBetting(gd.msg.split("_")[1],go);
//					break;
//				case 2:
//					p3_bet_jokbo.setText("베팅 : "+ gd.btMoney);
//					betting_profileReset (gd, 2);
//					whatBetting(gd.msg.split("_")[1],go);
//					break;
//				case 3:
//					p4_bet_jokbo.setText("베팅 : "+ gd.btMoney);
//					betting_profileReset (gd, 3);
//					whatBetting(gd.msg.split("_")[1],go);
//					break;
//				case 4:
//					p5_bet_jokbo.setText("베팅 : "+ gd.btMoney);
//					betting_profileReset (gd, 4);
//					whatBetting(gd.msg.split("_")[1],go);
//					break;
//				}
//				
//			
//				
//			}
//			
//			switch (gd.msg) {
//			


//			
//			
//				


//			} 
//			
//			
//		
//
//	    	repaint();
//	    
//			break;
//
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
    
    
    void gameRes_DBInsert (GameData data){
    	new SignDB().resInsert(myData);
    }
    //
    // 베팅한 사람의 프로필 리셋
    void betting_profileReset (GameData gd, Integer playerNum) {
    	
		
			System.out.println("이사람");
			switch (playerNum) {
			
			case 0:
				Ingame_userProfile_panel_0.userMoney = gd.money;
				Ingame_userProfile_panel_0.userMoney_text.setText("보유머니 : " + gd.money);
					

				break;
			case 1:
				Ingame_userProfile_panel_1.userMoney = gd.money;
				Ingame_userProfile_panel_1.userMoney_text.setText("보유머니 : " + gd.money);
				
				break;
			case 2:
				
				Ingame_userProfile_panel_2.userMoney = gd.money;
				Ingame_userProfile_panel_2.userMoney_text.setText("보유머니 : " + gd.money);
			
				break;
			case 3:

				Ingame_userProfile_panel_3.userMoney = gd.money;
				Ingame_userProfile_panel_3.userMoney_text.setText("보유머니 : " + gd.money);
			
				break;
			case 4:
				Ingame_userProfile_panel_4.userMoney = gd.money;
				Ingame_userProfile_panel_4.userMoney_text.setText("보유머니 : " + gd.money);
			
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

