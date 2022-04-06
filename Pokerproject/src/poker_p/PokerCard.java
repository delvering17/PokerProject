package poker_p;

import javax.swing.JLabel;

import game_p.Game_panel;

public class PokerCard  extends JLabel{
	Game_panel game_panel;
	int number;
	int shape;
	
	public PokerCard(int number, int shape) {
		this.number = number;
		this.shape = shape;
		
	}
}
