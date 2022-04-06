package lobby_i;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import DB_p.ProfileDTO;
import lobby_p.Lobby;
import lobby_p.LobbyMain;
import login_p.Login_frame;


public interface RoomAction {
	void room(HashMap<InetAddress, Object> roomChk,Login_frame mainJf,ObjectOutputStream oos,
	ObjectInputStream ois,ProfileDTO data,int addr);
}
