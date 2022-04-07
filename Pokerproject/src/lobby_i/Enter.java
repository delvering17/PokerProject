package lobby_i;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;

import DB_p.ProfileDTO;
import game_p.Game_panel;
import lobby_p.Lobby;
import lobby_p.LobbyMain;
import login_p.Login_frame;
import net_p.Receiver;
import net_p.TCPData;

public class Enter implements RoomAction{

	@Override
	public void room(Login_frame login_frame,Receiver ch,TCPData tcpdata,Integer addr) {
		
		login_frame.remove(login_frame.lobby_panel);
		Game_panel game_panel = new Game_panel(login_frame,ch,tcpdata);
		login_frame.add(game_panel);
		login_frame.game_panelarr.add(game_panel);
		login_frame.repaint();
		
	}

}
