package game_p;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import game_p.Game_panel;

public class PokerCard  extends JLabel{
	Game_panel game_panel;
	int number;
	int shape;
	String imgname;
	JLabel cardimg;
	public PokerCard(int number, int shape,JLabel cardimg) {
		this.number = number;
		this.shape = shape;
		this.cardimg = cardimg ;
		
		imgname = "Card"+number+"_"+shape;
		
	}

}
