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
		
		tcpdata.test.get(tcpdata.UserPos).put(num, tcpdata.name);
		
		if(num==0) {
			JButton GameStart = new JButton("게임 시작");
			GameStart.setBounds(500, 280, 200, 80);
			add(GameStart);
			GameStart.addActionListener(new ActionListener() {
				//게임 시작 버튼
				@Override
				public void actionPerformed(ActionEvent e) {
					tcpdata.DataDestination = "Game";
					tcpdata.userCount = num;
					for (int i = 2; i < 15; i++) {
						for (int j = 1; j < 5; j++) {
							tcpdata.dealerDeck.add(new PokerCard(i,j));
						}
					}
					GameProcess();
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
					tcpdata.test.get(tcpdata.UserPos).remove(num); 
					login_frame.add(new Lobby(login_frame,tcpdata,ch));
					login_frame.remove(game_panel);
					login_frame.repaint();
				}
			}
		});
		

		player1cardShow = new ArrayList<PlayerCard_Label>();
		p1 = new PlayerCard_panel(420, 520, 290, 200);
		add(p1);	
		
		
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
		player3cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			player3cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player3cardShow.size()-1 ; i >= 0 ; i--) {
			p3.add(player3cardShow.get(i));
		}
		add(p3);
		p4 = new PlayerCard_panel(710, 50, 290, 200);
		player4cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			player4cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player4cardShow.size()-1 ; i >= 0 ; i--) {
			p4.add(player4cardShow.get(i));
		}
		add(p4);
		p5 = new PlayerCard_panel(710, 280, 290, 200);
		player5cardShow = new ArrayList<PlayerCard_Label>();
		for (int i = 170 ; i >= 20 ; i -= 25) {
			player5cardShow.add(new PlayerCard_Label(i, 0, 100, 200));
		}
		for(int i = player5cardShow.size()-1 ; i >= 0 ; i--) {
			p5.add(player5cardShow.get(i));
		}
		add(p5);
		ttt = new ArrayList<ArrayList<PlayerCard_Label>>();

		

		
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
				if(e.getActionCommand().equals("콜")) {
					tcpdata.DataDestination = "Game";
					tcpdata.bettingMoney.set(num, panMoney);
					tcpdata.wholeBettingMoney += panMoney;
					tcpdata.msg = "betting_call";
					System.out.println("버튼 누르기전"+tcpdata.callCount);
					tcpdata.callCount++;
					System.out.println("버튼 누를때"+tcpdata.callCount);
					System.out.println(tcpdata.test.get(tcpdata.UserPos).keySet().size()-1+"leng");
					tcpdata.userCount = num;
					
					if(tcpdata.callCount == tcpdata.test.get(tcpdata.UserPos).keySet().size()-1&&tcpdata.playerDeck.get(0).size()==7) {
						System.out.println("종료입니다.");
					}else if(tcpdata.callCount == tcpdata.test.get(tcpdata.UserPos).keySet().size()-1) {
						oneSplit();
					}else {
						ch.send(tcpdata);
					}
				}
				
			}
		});
    	bt1 = new BetBtn("삥",85,0);
    	bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand().equals("삥")) {
					tcpdata.callCount=0;
					tcpdata.userCount = num;
					tcpdata.DataDestination = "Game";
					tcpdata.bettingMoney.set(num, panMoney);
					tcpdata.wholeBettingMoney += panMoney;
					tcpdata.msg = "betting";
					
					ch.send(tcpdata);
				}
				
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
			this.tcpdata.userCount = data.userCount;
			this.tcpdata.callCount = data.callCount;
			System.out.println(data.callCount+"----------------------올때");
		}
	
		switch (data.DataDestination) {
	
		case "Game":
			switch (data.msg) {
			
			case "카드 나와라":
				//확인필요
				for (Integer z : data.test.get(tcpdata.UserPos).keySet()) {
					for (int i = 2 ; i >= 0 ; i--) {
						ttt.get(z).get(i).setIcon(data.playerDeck.get(z).get(i).img);
					}					
				}

				break;
			case "betting" :
				this.tcpdata.bettingMoney = data.bettingMoney;
				this.bettingMoney = data.bettingMoney;
				this.tcpdata.wholeBettingMoney = data.wholeBettingMoney;
				this.wholeBettingMoney = data.wholeBettingMoney;
				this.tcpdata.panMoney = data.panMoney;
				this.panMoney = data.panMoney;
				betting.setText("전체 베팅금액: "+data.wholeBettingMoney+ ", 현재 판돈: "+ data.panMoney);

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

				if(data.callCount == data.test.get(tcpdata.UserPos).keySet().size()-1) {
					System.out.println("도냐~?");
					for (Integer z : data.test.get(tcpdata.UserPos).keySet()) {
						System.out.println("asdfsefwsedrgvedrgjdrgkdhkgdgdkjljdgfljgdbkrdhfgeklbdhklerkljd");
						ttt.get(z).get(tcpdata.playerDeck.get(z).size()-1).setIcon(data.playerDeck.get(z).get(tcpdata.playerDeck.get(z).size()-1).img);
					}
				}
				playerturn();
				break;	
			default :
				System.out.println("빠져나감");
				break;
			} 
			
			
	    	repaint();
	    
			break;

		}
		repaint();
		
	}
	
	void oneSplit() {
		for (Integer i : tcpdata.test.get(tcpdata.UserPos).keySet()) {
			System.out.println("asda");
			System.out.println(tcpdata.dealerDeck.size());
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
    
    
    
    
    
    void resultWinner(String[] aa) {
    	String [] res = aa;
		System.out.println(res.toString());
    	
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


		int[] test = new int[res.length];
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < bb.size(); j++) {
				if(bb.get(j).equals(res[i])) {
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
		String winner = "";
		if (res[0].equals(res[1])) {
			winner = "무승부!";
		} else {
			winner = num +"번!";
			
		}
    	
    	tcpdata.DataDestination = "res";
		tcpdata.msg = "0번:"+res[0]+" 1번: "+res[1] +" 승: "+winner;	
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

