package game_p;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import game_p.Game_panel;

public class PokerCard  extends JLabel{
	int number;
	int shape;
	String imgname;
	public PokerCard(int number, int shape) {
		this.number = number;
		this.shape = shape;
		imgname = "Card"+number+"_"+shape;
		
	}

}
