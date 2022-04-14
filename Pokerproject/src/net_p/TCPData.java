package net_p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import DB_p.ProfileDTO;
import game_p.Ingame_userProfile_panel;
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
	public ArrayList<Integer> bettingMoney;
	public int btMoney;
	
	public int panMoney;
	public int wholeBettingMoney;
	public String winner;
	//
	public Ingame_userProfile_panel ppp;
	//전체 기본
	public int[] easyStudy; 
	public HashMap<Integer,Integer[]> playData;
	public HashSet<String> userName;
	
	//Game data
	public MyData rdata;			//돈,전적,게임채팅,전체카드,개인카드,상대카드,승패,카드오픈,족보,배팅,기본판돈,게임판돈,개인베팅금액;
	public GameData gamedata;
	public ArrayList<PokerCard> dealerDeck;
	public HashMap<Integer, ArrayList<PokerCard>> playerDeck;
	
	public HashMap<Integer,HashMap<Integer, Ingame_userProfile_panel>> userprofile; //  인게임 프로필 
	public ArrayList<String> ipjang;
	public HashMap<Integer, HashMap<Integer, String>> test;
	public Integer prebetMoney;
	
	public Integer callCount;
	public boolean last;
	public Integer userCount;
	
	public HashMap<Integer, Boolean> roomclose;
	public HashMap<Integer, String> result;
	public ArrayList<Integer> userNumber;
	
	public TCPData(ProfileDTO datathis) {
		userNumber = new ArrayList<Integer>();
		result = new HashMap<Integer, String>();
		roomclose = new HashMap<Integer, Boolean>();
		for (int i = 0; i < 9; i++) {
			roomclose.put(i, false);
		}
		last = false;
		prebetMoney = 0;
		callCount = 0;
		test = new HashMap<Integer, HashMap<Integer, String>>();
		for (int i = 0; i < 9; i++) {
			test.put(i,new HashMap<Integer, String>());
		}
		userName = new HashSet<String>();
		dealerDeck = new ArrayList<PokerCard>();
		playerDeck = new HashMap<Integer, ArrayList<PokerCard>>();
		userprofile = new HashMap<Integer,HashMap<Integer, Ingame_userProfile_panel>>();
		for (int i = 0; i < 9; i++) {
			userprofile.put(i, new HashMap<Integer, Ingame_userProfile_panel>());
		}
		ipjang = new ArrayList<String>();
		bettingMoney = new ArrayList<Integer>();
		
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
