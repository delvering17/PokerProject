package lobby_i;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import lobby_p.Lobby;
import lobby_p.LobbyMain;
import login_p.Login_frame;


public interface RoomAction {
	void room(HashMap<InetAddress, Object> roomChk,Login_frame mainJf);
}
