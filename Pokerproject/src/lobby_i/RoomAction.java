package lobby_i;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import lobby_p.Lobby;
import lobby_p.LobbyMain;


public interface RoomAction {
	void room(HashMap<InetAddress, Object> roomChk,LobbyMain mainJf);
}
