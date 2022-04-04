package DB_p;

public class ProfileDTO {
	
	public int profilenum;
	public String nickname;
	public String gender;
	public String introduce;
	////갓 찬욱
	public String msg;
	public int totalGame;
	public int win;
	public int lose;
	public long money;
	
	public ProfileDTO(int profilenum, String nickname, String gender, String introduce, int totalGame, int win,
			int lose, long money) {
		super();
		this.profilenum = profilenum;
		this.nickname = nickname;
		this.gender = gender;
		this.introduce = introduce;
		this.totalGame = totalGame;
		this.win = win;
		this.lose = lose;
		this.money = money;
	}

	public ProfileDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getProfilenum() {
		return profilenum;
	}

	public void setProfilenum(int profilenum) {
		this.profilenum = profilenum;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getTotalGame() {
		return totalGame;
	}

	public void setTotalGame(int totalGame) {
		this.totalGame = totalGame;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}



	
	
	
}
