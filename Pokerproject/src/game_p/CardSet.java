package game_p;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CardSet {
	
	public CardSet() {
		
	
	
	}
	
	ArrayList<PokerCard> go() {
		ArrayList<PokerCard> deck = new ArrayList<PokerCard>();
		for (int i = 2; i < 15; i++) {
			for (int j = 1; j < 5; j++) {
				deck.add(new PokerCard(i,j,new JLabel(new ImageIcon("card/Card"+i+"_"+j+".png"))));
			}
		}
		
		return deck;
		
	}
	
}
