package net_p;

import java.io.Serializable;

import DB_p.ProfileDTO;

public class MyData  implements Serializable{
	
	private static final long serialVersionUID = 23L;
	public Integer pos;
	public String nickName;
	public int totalGame,win,lose,money;
	public Integer [] easyStudy;
	
	public MyData(ProfileDTO profiledto) {
		this.pos = -1;
		//this.playerNum = null;
		this.nickName = profiledto.nickname;
		//this.gender = profiledto.gender;
		this.totalGame = profiledto.totalGame;
		this.win = profiledto.win;
		this.lose = profiledto.lose;
		this.money = (int)profiledto.money;
	}
} 
