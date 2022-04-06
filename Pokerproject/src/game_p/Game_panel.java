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

import DB_p.ProfileDTO;
import lobby_p.Lobby;
import login_p.Login_frame;





public class Game_panel extends JPanel implements ActionListener {
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
	ProfileDTO data;
	ObjectOutputStream oos;
	ObjectInputStream ois;
	ArrayList<Ingame_userProfile_panel> userprofile = new ArrayList<Ingame_userProfile_panel>();
	Login_frame login_frame;
	Game_panel game_panel;
	PokerGameMain pokerGamemain;
	
	public Game_panel(Login_frame login_frame,ObjectOutputStream oos,ObjectInputStream ois,ProfileDTO data,int addr) {
		this.data = data;
		this.login_frame = login_frame;
		game_panel =this;
		data.roomNum=addr;
		setBounds(0, 0, 1200, 800);
		setBackground(new Color(32,56,48));
		setLayout(null);
		this.oos = oos;
		this.ois = ois;
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
						data.roomNum = 0;
						try {
							oos.writeObject(data);
							oos.flush();
							oos.reset();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						login_frame.remove(game_panel);
						login_frame.add(new Lobby(login_frame, data, oos, ois));
						
						login_frame.repaint();
					}
//					dispose(); // 원래있던 창이 꺼지며 새로운 창 나오게함
//					Exit_pg ex_pg = new Exit_pg();
					
			}
			
		});
		
		
		p1 = new PlayerCard_panel(420, 520, 290, 200);
		add(p1);
		p2 = new PlayerCard_panel(200, 50, 290, 200);
		add(p2);
		p3 = new PlayerCard_panel(200,280,290,200);
		add(p3);
		p4 = new PlayerCard_panel(710, 50, 290, 200);
		add(p4);
		p5 = new PlayerCard_panel(710, 280, 290, 200);
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
		
		JPanel betbuttonarea = new JPanel(new GridLayout(4,2));
        betbuttonarea.setBounds(740,520,170,220);
        add(betbuttonarea);
       
        String [] betbt = {"콜","삥","따당","하프","다이","체크","맥스"};
        for (int i = 0; i < 7; i++) {
            bt = new JButton(betbt[i]);
            bt.setBackground(Color.white);
            betbuttonarea.add(bt);
            
           // bt.addActionListener(this);
          
            // 버튼 누를때 액션 하다말았음
            bt.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						Long qua = (long) 100000;
					
					try {
						qua = Long.parseLong(JOptionPane.showInputDialog(" 금액을 입력해주세요."));
						if(qua<1000000) {
							System.out.println("10만 이상부터 배팅 가능");
							
						} 
					} catch(Exception e1) {
						System.out.println("숫자를 입력해주세요.");
						
					}
					  
				}
			});
        }
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
		chf.setBounds(920, 550, 220, 20);
		chf.addActionListener(this);
		
		cht = new JTextArea();
		login_frame.jta = cht;
		JScrollPane jp = new JScrollPane(cht);
		jp.setBounds(920, 580, 220, 20);
		cht.setEditable(false);
		
		chat.add(chf,BorderLayout.SOUTH);
		chat.add(jp,BorderLayout.CENTER);
		
		chf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					data.msg = chf.getText();
					oos.writeObject(data);
					oos.flush();
					oos.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cht.setCaretPosition(cht.getDocument().getLength());
				chf.setText("");
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
	}
}



