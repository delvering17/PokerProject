package game_p;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Jokbo {
	
	
	
	public Jokbo() {
		
		
		
		
	}
	
	int straightcon = 0;
	
	void straightCon_Method (ArrayList<PokerCard> arr, int max) {

		if(arr.get(0).number == max) {			// remove해주는데 만약 인덱스가 0밖에 없다면? 
			
		} else {
			if (arr.get(0).number+1 == arr.get(1).number) {	 //   연속 숫자 거르기
				arr.remove(0);
				straightcon++;
				straightCon_Method(arr,max);
			} else {
				if (arr.get(0).number == arr.get(1).number){ // 중복 숫자 거르기
					arr.remove(0);
					straightCon_Method(arr,max);
				} else {									// 비연속 숫자 거르기
				
					if (straightcon >=4) {
						
					} else {
						straightcon = 0;
						arr.remove(0);
						straightCon_Method(arr,max);
					}
					
				}
			}	
		}
	}
	
	
	String jokbo(ArrayList<PokerCard> myDeck) {
		String res = "";
		int top =0;
		int pareCount = 0;
		int subCount = 0;
		int preNum = 0;
		int thisNum = 0;
		int diamond = 0, clover = 0,spade = 0, heart = 0;
		ArrayList<PokerCard> compare = new ArrayList<PokerCard>();
		ArrayList<PokerCard> straight = new ArrayList<PokerCard>();
		ArrayList<PokerCard> teststraight = new ArrayList<PokerCard>();
		for (PokerCard pkc : myDeck) {
			compare.add(pkc);
			switch (pkc.shape) {
			case 1:
				clover++;
				break;
			case 2:
				heart++;
				break;
			case 3:
				diamond++;
				break;
			case 4:
				spade++;
				break;
			}
		}
		for (PokerCard pkc : myDeck) {
			straight.add(pkc);
		}
		
		
		System.out.println("----");

		
		
		//페어
		for (int i = 0; i < compare.size(); i++) {
			for (int j = i+1; j < compare.size(); j++) {
				if(pareCount==0) {
					if(compare.get(i).number==compare.get(j).number) {
						preNum = i;
						thisNum = j;
						res = "원페어";
						pareCount++;
					}
					
				}
				
			}
		}
		for (int i = thisNum+1; i < compare.size(); i++) {
				if(compare.get(thisNum).number==compare.get(i).number) {
					pareCount++;
				}
		}
		switch (pareCount) {
		case 1:
			res="원페어";
			break;
		case 2:
			res="트리플";
			break;
		case 3:
			res="포카드";
			break;
		}
	
		if(pareCount != 0) {
			
			if(pareCount == 1) {
				compare.remove(compare.get(thisNum));
				compare.remove(compare.get(preNum));
				for (int i = 0; i < compare.size(); i++) {
					for (int j = i+1; j < compare.size(); j++) {
						if(compare.get(i).number==compare.get(j).number) {
							
							res="투페어";
							subCount++;
						}
					}
				}
			}else if(pareCount == 2) {
				compare.remove(compare.get(thisNum));
				compare.remove(compare.get(preNum));
				for (int i = 0; i < myDeck.size(); i++) {
					for (int j = i+1; j < compare.size(); j++) {
						if(compare.get(i).number==compare.get(j).number) {
							
							res="풀하우스";
							subCount++;
						}
					}
				}
			}
		}
		              
		//teststraight
		for (PokerCard card : myDeck) {
			teststraight.add(card);
		}
		
		Collections.sort(teststraight, new Comparator<PokerCard>() {
			@Override
			public int compare(PokerCard o1, PokerCard o2) {
				int res = 1; 
				if (o1.number <= o2.number) {
					res = -1;
				}
				
//				if ( (o1.number == o2.number) && (o1.shape <= o2.shape)) {
//					res = -1;
//				}
				
				
				return res;
				
		}}) 	;
		

		
		for (PokerCard aa : teststraight) {
			System.out.println(aa.number + "," + aa.shape);
		}
		System.out.println("----");
	
	
		straightCon_Method(teststraight, teststraight.get(teststraight.size()-1).number);
		
	
		
		if (straightcon >= 4) {
			res = "스트레이트";
		}
		
		
		
		//로얄,백스트레이트
		int stra = 0 ;
		int backst = 0;
		int royalst = 0;
		for (int i = 0; i < straight.size(); i++) {
			for (int j = i+1; j < straight.size(); j++) {
				if(straight.get(i).number==straight.get(j).number) {
					straight.remove(straight.get(j));
				}
			}
		}

		
		for (PokerCard pokerCard : straight) {
			switch (pokerCard.number) {
			case 2,3,4,5,14:
				backst++;
				break;
				
			default:
				break;
			}
			
		}
		for (PokerCard pokerCard : straight) {
			switch (pokerCard.number) {
			case 13,12,11,10,14:
				royalst++;
				break;
				
			default:
				break;
			}
			
		}
		if(backst==5) res="백스트레이트";
		if(royalst==5) res="로얄스트레이트";
		else if(backst!=5&&royalst!=5)
		//플러쉬
		if(diamond>=5||spade>=5||heart>=5||clover>=5) {
			if(res=="로얄스트레이트") {
				res="로얄스트레이트플러쉬";
			}else if(res=="백스트레이트") {
				res="백스트레이트플러쉬";
			}else if(res=="스트레이트"){
				res="스트레이트플러쉬";
			}else{
				res="플러쉬";				
			}
		}
		//탑
		for (int i = 0; i < myDeck.size(); i++) {  
			if(top<myDeck.get(i).number) {
				top=myDeck.get(i).number;
			}
		}
		if(res=="") {
			res=top+"탑";
		}
		
		return res;
	
	}
}

