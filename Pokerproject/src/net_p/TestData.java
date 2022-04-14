package net_p;

import java.io.Serializable;

public class TestData implements Serializable{
	
	private static final long serialVersionUID = 12L;
	
	
	public TestData(Integer pre, Integer pos, String nickName, Integer playerNum) {
		super();
		this.pre = pre;
		this.pos = pos;
		this.nickName = nickName;
		this.playerNum = playerNum;
	}
	
	
	public TestData() {
		// TODO Auto-generated constructor stub
	}
	public Integer pre,pos,playerNum;
	public String nickName;


	@Override
	public String toString() {
		return "TestData [pre=" + pre + ", pos=" + pos + ", playerNum=" + playerNum + ", nickName=" + nickName + "]";
	}
	
	
}
