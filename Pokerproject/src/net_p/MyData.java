package net_p;

import DB_p.ProfileDTO;

public class MyData {
	public Integer pos,playerNum;
	public String nickName,gender;
	public int totalGame,win,lose,money;
	
	public MyData(ProfileDTO profiledto,TCPData tcpdata) {
		this.pos = -1;
		this.playerNum = null;
		this.nickName = profiledto.nickname;
		this.gender = profiledto.gender;
		this.totalGame = profiledto.totalGame;
		this.win = profiledto.win;
		this.lose = profiledto.lose;
		this.money = (int)profiledto.money;
	}
}
