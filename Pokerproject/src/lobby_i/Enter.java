package lobby_i;

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

public class Enter implements RoomAction{

	@Override
	public void room(HashMap<InetAddress, Object> roomChk,Login_frame login_frame,ObjectOutputStream oos,
	ObjectInputStream ois,ProfileDTO data) {
		
//		for (Object chk : roomChk.values()) {
//			if(chk!=null) {
//				Object.arraylist.size() <4 {
//					new Objcet();
//				}
//			}
//		}
		
		login_frame.remove(login_frame.lobby_panel);
		Game_panel game_panel = new Game_panel(login_frame,oos,ois,data);
		login_frame.add(game_panel);
		login_frame.game_panelarr.add(game_panel);
		login_frame.repaint();
	}

}
