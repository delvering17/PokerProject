package net_p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import DB_p.ProfileDTO;
import game_p.PokerCard;

public class TCPData implements Serializable{
	
	// 목적  des String (Chatting, GameData)
	// 위치	UserPos int  -1  123456
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String name;				//닉네임
	public String DataDestination;	//Chatting , GameData 등등		--> 익스큐트에서 걸러짐 ,서버에서 확인
	public int UserPos;           	//자신의 위치 -1= 로비, 0=1번방		--> 서버에서 걸러짐
	public HashMap<Integer,Integer[]> playData;
	public String msg;
	public MyData rdata;			//돈,전적,게임채팅,전체카드,개인카드,상대카드,승패,카드오픈,족보,배팅,기본판돈,게임판돈,개인베팅금액;
	public int[] easyStudy; 
	public String panelChk;
	public GameData gamedata;
	
	//Game data
	public ArrayList<PokerCard> dealerDeck;
	public HashMap<Integer, ArrayList<PokerCard>> playerDeck;
	
	
	public TCPData(ProfileDTO datathis) {
		dealerDeck = new ArrayList<PokerCard>();
		playerDeck = new HashMap<Integer, ArrayList<PokerCard>>();
		gamedata = new GameData();
		easyStudy = new int[9];
		for (int i = 0; i < easyStudy.length; i++) {
			easyStudy[i] = 0;
		}
		name = datathis.nickname;
		UserPos = -1;
		playData = new HashMap<Integer,Integer[]>();
		for (int j = 0; j < 9; j++) {
			playData.put(j,new Integer[5]);
			for (int i = 0; i < playData.get(j).length; i++) {
				playData.get(j)[i] = -1;
			}
		}
	}
}
