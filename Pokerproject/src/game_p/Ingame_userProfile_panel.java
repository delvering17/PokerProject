package game_p;

import javax.swing.JLabel;
import javax.swing.JPanel;

import DB_p.ProfileDTO;
import DB_p.SignDB;

public class Ingame_userProfile_panel extends JPanel {
	ProfileDTO profileDTO;
	JLabel nickname_text;
	int totalGame;
	int win;
	int lose;
	long userMoney;
	
	public Ingame_userProfile_panel(int playerNum, int profilenum) {
		
		this.profileDTO = playerDTO_get(profilenum);

		//
		this.totalGame = profileDTO.totalGame;
		this.win = profileDTO.win;
		this.lose = profileDTO.lose;
		this.userMoney = profileDTO.money;
		
		switch (playerNum) {
			
		case 1:
			setBounds(220, 520, 200, 200);
			break;
		case 2:
			setBounds(10, 50, 200, 200);
			break;
		case 3:
			setBounds(10, 280, 200, 200);
			break;
		case 4:
			setBounds(1000, 50, 200, 200);
			break;
		case 5:
			setBounds(1000, 280, 200, 200);
			break;
		
		
		}
	}
	
	ProfileDTO playerDTO_get(int profilenum) {
		
		ProfileDTO profileDTO = new SignDB().num_profileRead(" profilenum = " + profilenum + ";");
		return profileDTO;
		
	}
	
}
