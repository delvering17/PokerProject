package net_p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import DB_p.ProfileDTO;
import game_p.PokerCard;

public class TCPData implements Serializable{
	
	// 목적  des String (Chatting, GameData)
	// 위치	UserPos int  -1  123456
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//자신 기본
	public String name,gender;				//닉네임
	public int totalGame;
	public int win;
	public int lose;
	public long money;
	public int UserPos;           	//자신의 위치 -1= 로비, 0=1번방		--> 서버에서 걸러짐
	public String msg;
	public String DataDestination;	//Chatting , GameData 등등		--> 익스큐트에서 걸러짐 ,서버에서 확인
	public String panelChk;
	public String[] res;
	//
	public int[] bettingMoney;
	public int panMoney;
	public int wholeBettingMoney;
	public String winner;
	//
	
	//전체 기본
	public int[] easyStudy; 
	public HashMap<Integer,Integer[]> playData;
	public HashSet<String> userName;
	
	//Game data
	public MyData rdata;			//돈,전적,게임채팅,전체카드,개인카드,상대카드,승패,카드오픈,족보,배팅,기본판돈,게임판돈,개인베팅금액;
	public GameData gamedata;
	public ArrayList<PokerCard> dealerDeck;
	public HashMap<Integer, ArrayList<PokerCard>> playerDeck;
	
	
	
	
	
	public TCPData(ProfileDTO datathis) {
		userName = new HashSet<String>();
		dealerDeck = new ArrayList<PokerCard>();
		playerDeck = new HashMap<Integer, ArrayList<PokerCard>>();
		for (int i = 0; i < 5; i++) {
			playerDeck.put(i, new ArrayList<PokerCard>());
		}
		gamedata = new GameData();
		easyStudy = new int[9];
		for (int i = 0; i < easyStudy.length; i++) {
			easyStudy[i] = 0;
		}
		name = datathis.nickname;
		
		
		this.gender = datathis.gender;

		this.totalGame = datathis.totalGame;
		this.win = datathis.win;
		this.lose = datathis.lose;
		this.money = datathis.money;
		
		
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
