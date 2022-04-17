package net_p;

import java.io.Serializable;

public class RoomChk implements Serializable{

	private static final long serialVersionUID = 1234L;
	
	public int roomNum;
	public boolean bl;
	public RoomChk(int roomNum, boolean bl) {
		this.roomNum = roomNum;
		this.bl = bl;
	}
}
