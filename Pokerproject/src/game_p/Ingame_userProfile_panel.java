package game_p;

import javax.swing.JLabel;
import javax.swing.JPanel;

import DB_p.ProfileDTO;
import DB_p.SignDB;

public class Ingame_userProfile_panel extends JPanel {
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
		nickname_text.setBounds(0,0,200,50); 
		nickname_text.setText("닉네임 : " + nickname);
		add(nickname_text);
		
		win_text = new JLabel();
		win_text.setBounds(0,50,200,50);  
		win_text.setText("승 : " + win);
		add(win_text);
		
		lose_text = new JLabel();
		lose_text.setBounds(0,100,200,50); 
		lose_text.setText("패 : " + lose);
		add(lose_text);
		
		userMoney_text = new JLabel();
		userMoney_text.setBounds(0,150,200,50); 
		userMoney_text.setText("보유머니 : " + userMoney);
		add(userMoney_text);
		
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
