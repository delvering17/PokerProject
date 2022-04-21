package game_p;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import DB_p.ProfileDTO;
import DB_p.SignDB;

public class Ingame_userProfile_panel extends JLabel {
	ProfileDTO profileDTO;
	JLabel nickname_text;
	JLabel win_text;
	JLabel lose_text;
	JLabel userMoney_text;
	int totalGame;
	int win;
	int lose;
	long userMoney;
	int playerNum;
	
	public Ingame_userProfile_panel(int playerNum, String nickname) {
		
		this.profileDTO = playerDTO_get(nickname);

		//
		this.totalGame = profileDTO.totalGame;
		this.win = profileDTO.win;
		this.lose = profileDTO.lose;
		this.userMoney = profileDTO.money;
		this.playerNum = playerNum;
		setLayout(null);
		nickname_text = new JLabel();
		nickname_text.setBounds(0,25,200,40); 
		nickname_text.setFont(new Font("Pretendard-SemiBold",Font.BOLD,20));
		nickname_text.setText("   닉네임 : " + nickname);
		add(nickname_text);
		
		win_text = new JLabel();
		win_text.setBounds(0,50,200,50);  
		win_text.setFont(new Font("Pretendard-SemiBold",Font.BOLD,20));
		win_text.setText("   승 : " + win+" 패 : " + lose);
		add(win_text);
		
//		lose_text = new JLabel();
//		lose_text.setBounds(0,100,200,50); 
//		lose_text.setFont(new Font("Pretendard-SemiBold",Font.BOLD,20));
//		lose_text.setText("        패 : " + lose);
//		add(lose_text);
		
		userMoney_text = new JLabel();
		userMoney_text.setBounds(20,90,200,50); 
		userMoney_text.setFont(new Font("Pretendard-SemiBold",Font.BOLD,15));
		userMoney_text.setText("보유머니 : " + userMoney);
		add(userMoney_text);
        ImageIcon aa = new ImageIcon("img/gamepanel/ingameprofile.png");
        setIcon(aa);
		switch (playerNum) {
			
		case 0:
			setBounds(220, 520, 200, 200);
			break;
		case 1:
			setBounds(0, 50, 200, 200);
			break;
		case 2:
			setBounds(0, 280, 200, 200);
			break;
		case 3:
			setBounds(1000, 50, 200, 200);
			break;
		case 4:
			setBounds(1000, 280, 200, 200);
			break;
		
		
		}
	}
	
	public Ingame_userProfile_panel(int playerNum) {
		
		setLayout(null);
		switch (playerNum) {
		
		case 0:
			setBounds(220, 520, 200, 200);
			break;
		case 1:
			setBounds(0, 50, 200, 200);
			break;
		case 2:
			setBounds(0, 280, 200, 200);
			break;
		case 3:
			setBounds(1000, 50, 200, 200);
			break;
		case 4:
			setBounds(1000, 280, 200, 200);
			break;
		
		
		}
		
	}

	ProfileDTO playerDTO_get(String nickname) {
		
		ProfileDTO profileDTO = new SignDB().num_profileRead(" nickname = '" + nickname + "';");
		return profileDTO;
		
	}
	
}