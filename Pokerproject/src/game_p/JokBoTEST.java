package game_p;

import java.io.Serializable;
import java.util.ArrayList;

public class JokBoTEST implements Serializable{

	private static final long serialVersionUID = 5L;

//	ArrayList<String> resWin;
	public JokBoTEST() {
//		JokBoTEST aa = new JokBoTEST();

		
//		resWin = new ();

		
//		for (int i = 0 ; i < res[].length ; i++) {
//			resWin.add(카드 스트링 결과 new Jokbo());
			
//		}
//		reWin = {"풀하우스","원페어"}
//		resultWinner();
	}
   void resultWinner(ArrayList<String> resWin) {
	   ArrayList<String> res = resWin;
    	
    	
		//System.out.println(res.toString());
    	
    	ArrayList<String> bb = new ArrayList<String>();
    
		bb.add("탑");			
		bb.add("원페어");	// 같고높은 숫자
		bb.add("투페어"); // 가장 높은 숫자
		bb.add("트리플");	 // 같은높은 숫자
		bb.add("스트레이트"	);		//  2~9로 시작하는 스트레이트 , 시작 숫자 모양
		bb.add("백스트레이트");		// a 2345 -> a 문양
		bb.add("로얄스트레이트");  // 10JQKA ->  A 문양 
		bb.add("플러쉬");				// 숫자 
		bb.add("풀하우스");			// 3장에서 가장 높은 숫자  
		bb.add("포카드");				// 높은 숫자 
		bb.add("스트레이트플러쉬");			//  시작 숫자 모양 
		bb.add("백스트레이트플러쉬");			// 모양 
		bb.add("로얄스트레이트플러쉬");		// 모양  10JQKA
		

		int[] test = new int[res.size()];
		for (int i = 0; i < test.length; i++) {
			for (int j = 0; j < bb.size(); j++) {
				if(bb.get(j).equals(res.get(i).split("_")[0])) {
					test[i]=j; 
					System.out.println(j);
				}
			}
		}
		// test = 9, 0 
		int num = 0;
		int aaa = 0;
		int win = -1;
		
		
		for (int i = 0; i < test.length; i++) {
			if(test[i]>aaa) {
				aaa=test[i];
				num = i;
				win =i;
				
			} else if (test[i] == aaa)  {
				int a = Integer.parseInt(res.get(i).split("_")[1].split(",")[0]);
				int b = Integer.parseInt(res.get(num).split("_")[1].split(",")[0]);
				if (a > b) {
					win = i;
					
				} else if (a < b) {
					win = num;
				} else if (a == b) {
					int c = Integer.parseInt(res.get(i).split("_")[1].split(",")[1]);
					int d = Integer.parseInt(res.get(num).split("_")[1].split(",")[1]);
					if (c > d) {
						win = i;
					} else {
						win = num;
					}
				}
				
			}
		}
	
		String winner = "";
		if (res.get(0).equals(res.get(1))) {
			winner = "무승부!";
		} else {
			winner = num +"번!";
			
		}
		
		System.out.println(win);
		
//    	
//    	tcpdata.DataDestination = "res";
//		tcpdata.msg = "0번:"+res[0]+" 1번: "+res[1] +" 승: "+winner;	
//		ch.send(tcpdata);
    }
		


}