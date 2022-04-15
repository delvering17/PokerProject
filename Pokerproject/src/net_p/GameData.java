package net_p;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import game_p.Ingame_userProfile_panel;
import game_p.PokerCard;

public class GameData implements Serializable{
	
	private static final long serialVersionUID = 22L;
	
	public HashMap<String, Integer> game_users ;

	public Integer playerNum, bettingMoney;
	public ArrayList<PokerCard> dealerDeck;
	public HashMap<Integer, ArrayList<PokerCard>> playerDeck;
	public ArrayList<Ingame_userProfile_panel> userprofile; //  인게임 프로필 
	public Integer prebetMoney;
	public Integer callCount;
	public Integer userCount;
	public int btMoney;
	public int panMoney, money;
	public int wholeBettingMoney;
	public String winner, nickName,msg;
	public boolean last;
	public HashMap<Integer, String> result;
	public HashMap<Integer, Boolean> roomclose;
}