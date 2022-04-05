package DB_p;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class test {
	

	public static void main(String[] args) {
		test aa = new test();
		aa.play();
		
	}
	
	public void play() {
		
		ArrayList<Card> dealer = new ArrayList<Card>();
		
		dealer = new Shuffle().dealerShuffle(dealer);
		
		for (Card aa : dealer ) {
			
			System.out.println(aa.num + "," + aa.shape);
		}
		//Collections.sort(dealer);
		
		Player p1 = new Player();
		
		p1.cardSet.add(new Card(2,3));
		p1.cardSet.add(new Card(1,4));
		p1.cardSet.add(new Card(3,2));
		p1.cardSet.add(new Card(3,3));
		Collections.sort(p1.cardSet);
		System.out.println(p1.cardSet.get(0).num + "," + p1.cardSet.get(0).shape);
//		2 2 3 3
		for (int i = 0 ; i < p1.cardSet.size() ; i ++) {
			for (int j = 0 ; j < p1.cardSet.size() ; j++) {
				
			}
			
		}
		
	}
	
}

class Card implements Comparable<Card>{
	int num;  // 2 3 4 5 6 7 8 9 10 11j 12q 13k 14a
	int shape;// 1c 2h 3d 4s
	
	public Card(int num, int shape) {
		this.shape = shape;
		this.num = num;
	}

	@Override
	public int compareTo(Card card) {
		// TODO Auto-generated method stub
		if (card.num < num) {	
			return 1;
			} else if (card.num > num) {
			return -1;
			}
			return 0;
	}

}

class Player {
	ArrayList<Card> cardSet = new ArrayList<Card>();
	
	
	public Player() {
		
	}
	
}

//셔플 클래스
class Shuffle {
	
	public Shuffle() {
		
	}
	
	ArrayList<Card> dealerShuffle(ArrayList<Card> cardSet) {
		ArrayList<Card> recardSet = cardSet;
		
		for (int i = 1 ; i < 5 ; i++) {
			for (int j = 2 ; j < 15 ; j++ ) {
				recardSet.add(new Card(i,j));
			}
		}
		
		return recardSet;
		
	}
	
	void FirstCard() {
		
	} 
	
}

class Calcultation {
	
	public Calcultation() {
	
	}
	// 로열 스트레이트 플러시 무늬 o 10 j q k a
	
	// 백 스트레이트 플러시  무늬o  a2345
	
	// 스트레이트 무늬 O 연속 숫자
	
	// 포카드 4장 숫자

	// 풀하우스 트리플 + 원페어
	
	// 플러시 무늬 5장
	
	// 마운틴  무늬 x 10 j q k a
	
	// 스트레이트 5장 연속 숫자
	
	// 트리플
	
	// 투페어
	
	// 원페어
	
	// 
	
	
}





