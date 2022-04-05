package lobby_i;

import java.io.Serializable;

public class UserData implements Serializable{
	
	private static final long serialVersionUID = 87654L;
	
	public String id,name,mony,msg;

	public UserData(String id, String name, String mony,String msg) {
		this.id = id;
		this.name = name;
		this.mony = mony;
		this.msg = msg;
	}
}
