package net_p;

import java.io.Serializable;

public class MsgData implements Serializable{
	
	private static final long serialVersionUID = 22L;
	
	public String msg,nickName;
	public MsgData(String nickName,String msg) {
		this.nickName =nickName;
		this.msg = msg;
	}
	@Override
	public String toString() {
		return nickName + " : " + msg;
	}
	
}
