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

public class Enter implements RoomAction{

	@Override
	public void room(HashMap<InetAddress, Object> roomChk,Login_frame login_frame,ObjectOutputStream oos,
	ObjectInputStream ois,ProfileDTO data,int addr) {
		int chk = 0;
		while(true) {
			if(ProfileDTO.roomchk[chk]<5&&ProfileDTO.roomchk[chk]>0) {
				ProfileDTO.roomchk[chk]++;
				login_frame.remove(login_frame.lobby_panel);
				data.roomNum = chk+1;
				try {
					oos.writeObject(data);
					oos.flush();
					oos.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Game_panel game_panel = new Game_panel(login_frame,oos,ois,data,addr);
				login_frame.add(game_panel);
				login_frame.game_panelarr.add(game_panel);
				login_frame.repaint();
				break;				
			}else if(chk == 8) {
				login_frame.remove(login_frame.lobby_panel);
				data.roomNum = 1;
				try {
					oos.writeObject(data);
					oos.flush();
					oos.reset();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Game_panel game_panel = new Game_panel(login_frame,oos,ois,data,addr);
				login_frame.add(game_panel);
				login_frame.game_panelarr.add(game_panel);
				login_frame.repaint();
				break;
			}
			chk++;
		}
		
		
//		login_frame.remove(login_frame.lobby_panel);
//		data.roomNum = addr;
//		try {
//			oos.writeObject(data);
//			oos.flush();
//			oos.reset();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		Game_panel game_panel = new Game_panel(login_frame,oos,ois,data,addr);
//		login_frame.add(game_panel);
//		login_frame.game_panelarr.add(game_panel);
//		login_frame.repaint();
	}

}
