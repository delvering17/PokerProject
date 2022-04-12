package game_p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Jokbo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public Jokbo() {
		
		
		
		
	}
	
	int straightcon;
	int straightNum;
	int straightShape;
	String straightLow;
	void straightCon_Method (ArrayList<PokerCard> arr, int max) {

		if(arr.get(0).number == max) {			// remove해주는데 만약 인덱스가 0밖에 없다면? 
			
		} else {
			if (arr.get(0).number+1 == arr.get(1).number) {	 //   연속 숫자 거르기
				if (straightcon == 0) {
					straightNum = arr.get(0).number;
					straightShape = arr.get(0).shape; 
					straightLow = straightNum + "," + straightShape;
					
				}
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
		ArrayList<PokerCard> fourCard = new ArrayList<PokerCard>();
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
			fourCard.add(pkc);
		}
		Collections.sort(compare, new Comparator<PokerCard>() {
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
		

		String pare = "";
		
		//페어
		for (int i = 0; i < compare.size(); i++) {
			for (int j = i+1; j < compare.size(); j++) {
				if(pareCount==0) {
					if(compare.get(i).number==compare.get(j).number) {
						preNum = i;
						thisNum = j;
						res = "원페어_"+compare.get(i).number+","+compare.get(i).shape;
						pare = compare.get(i).number+","+compare.get(i).shape;
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
			res="원페어_"+pare;
			break;
		case 2:
			res="트리플_"+pare;
			break;
		case 3:
			res="포카드_"+pare;
			break;
		}
		
	
		int twopareNum = 2;
		int twopareShape = 1;
		String twopareHigh = "";
		if(pareCount != 0) {
			
			if(pareCount == 1) {
		
				compare.remove(compare.get(thisNum));
				compare.remove(compare.get(preNum));
				BIG: for (int i = 0; i < compare.size(); i++) {
					for (int j = i+1; j < compare.size(); j++) {
						if(compare.get(i).number==compare.get(j).number) {
							
							
							if (twopareNum <= compare.get(i).number) {
								twopareNum = compare.get(i).number;
								if (twopareShape <= compare.get(i).shape) {
									if (compare.get(i).shape<compare.get(j).shape ) {
										twopareHigh = compare.get(i).number+","+compare.get(j).shape;
									} else {
										twopareHigh = compare.get(i).number+","+compare.get(i).shape;
										
									}
								}
							}
							
							res="투페어_"+twopareHigh;
							for(int a = j ; a < compare.size() ; a++) {
								if (a+1 == compare.size()) {
									
								} else { 
									if( compare.get(a-1).number == compare.get(a).number&& compare.get(a+1).number == compare.get(a).number) {
										res="풀하우스_"+twopareHigh;
										break ;
									} else {
						
									}
								}
								
								
							} 
							if(res.split("_")[0].equals("풀하우스")) {
								break BIG;
							}
							subCount++; // 안씀
						} else {
							
						}
					}
				}
			}else if(pareCount == 2) {
				compare.remove(compare.get(thisNum));
				compare.remove(compare.get(preNum));
				for (int i = 0; i < myDeck.size(); i++) {
					for (int j = i+1; j < compare.size(); j++) {
						if(compare.get(i).number==compare.get(j).number) {
							
							if (twopareNum <= compare.get(i).number) {
								twopareNum = compare.get(i).number;
								if (twopareShape <= compare.get(i).shape) {
									if (compare.get(i).shape<compare.get(j).shape ) {
										twopareHigh = compare.get(i).number+","+compare.get(j).shape;
									} else {
										twopareHigh = compare.get(i).number+","+compare.get(i).shape;
										
									}
								}
				
							res="풀하우스_"+twopareHigh;
							subCount++;
							}
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
		

	
		straightcon = 0;
		straightNum = 2;
		straightShape = 1;
		straightLow = "";
	
		straightCon_Method(teststraight, teststraight.get(teststraight.size()-1).number);
		
	
		
		if (straightcon >= 4) {
			res = "스트레이트_"+straightLow;
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
		ArrayList<Integer> backCon = new ArrayList<Integer>();
		String backstHigh = "";
		for (PokerCard pokerCard : straight) {
			switch (pokerCard.number) {
			case 2,3,4,5,14:
				
				if (!backCon.contains(pokerCard.number)) {
					backCon.add(pokerCard.number);
					backst++;
					if(pokerCard.number ==14) {
						backstHigh = pokerCard.number + "," + pokerCard.shape;
					}
				} else {
					
				}
			
				break;
				
			default:
				break;
			}
			
		}
		
		ArrayList<Integer> royalCon = new ArrayList<Integer>();
		String royalstHigh ="";
		for (PokerCard pokerCard : straight) {
			switch (pokerCard.number) {
			case 13,12,11,10,14:
			
				if (!royalCon.contains(pokerCard.number)) {
					royalCon.add(pokerCard.number);
					royalst++;
					if(pokerCard.number ==14) {
						royalstHigh = pokerCard.number + "," + pokerCard.shape;
					}
				} else {
					
				}
				break;
				
			default:
				break;
			}
			
		}
		if(backst==5) res="백스트레이트_"+backstHigh;
		if(royalst==5) res="로얄스트레이트_"+royalstHigh;
		else if(backst!=5&&royalst!=5)
		//플러쉬
		if(diamond>=5||spade>=5||heart>=5||clover>=5) {
			if(res=="로얄스트레이트") {
				res="로얄스트레이트플러쉬_"+royalstHigh;
			}else if(res=="백스트레이트") {
				res="백스트레이트플러쉬_"+backstHigh;
			}else if(res=="스트레이트"){
				res="스트레이트플러쉬_"+ straightLow;
			}else{
				if(diamond>=5) {
					res="플러쉬_"+"2,3";			
				} else if (spade>=5) {
					res="플러쉬_"+"2,4";	
				} else if (heart>=5) {
					res="플러쉬_"+"2,2";	
				} else if (clover>=5) {
					res="플러쉬_"+"2,1";	
				} 
						
			}
		}
		int topShape = 1;
		//탑
		for (int i = 0; i < myDeck.size(); i++) {  
			if(top<myDeck.get(i).number) {
				topShape = myDeck.get(i).shape;
				top=myDeck.get(i).number;
			}
		}
		if(res=="") {
			res="탑_"+top+","+topShape;
		}
		
		int fourCardres = 0; 
		int four = fourCard.get(0).number;
		// 2 2 2 2 1 5 7
		BIG: for (int i = 0 ; i < fourCard.size() ; i++) {
			four = fourCard.get(i).number;
			for (int j = 0 ; j < fourCard.size() ; j++) {
				if (fourCard.get(j).number == four) {
					
					fourCardres++;
					if (fourCardres > 3) {
						res = "포카드_"+fourCard.get(j).number+ "," + "2" ;
						break BIG;
					}
				}	
			}
			
			fourCardres = 0;
		}   
	
		
		
		
		return res;
	
	}
}