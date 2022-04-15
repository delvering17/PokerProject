package net_p;

import java.io.Serializable;

public class UserData implements Serializable{
	
	private static final long serialVersionUID = 12L;
	
	public UserData(Integer pre, Integer pos, String nickName, Integer playerNum) {
		super();
		this.pre = pre;
		this.pos = pos;
		this.nickName = nickName;
		this.playerNum = playerNum;
	}

	public Integer pre,pos,playerNum;
	public String nickName;

	@Override
	public String toString() {
		return "TestData [pre=" + pre + ", pos=" + pos + ", playerNum=" + playerNum + ", nickName=" + nickName + "]";
	}
	
	
}
