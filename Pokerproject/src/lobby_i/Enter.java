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
	public void room(Login_frame login_frame,Receiver ch,Lobby lobby,TCPData tcpdata,Integer addr) {
		int chk =0;
		CHK:while(true) {
			if(tcpdata.easyStudy[chk]>0&&tcpdata.easyStudy[chk]<=5) {
//				tcpdata.DataDestination = "RoomChk";
//				tcpdata.easyStudy[chk]++;
//				tcpdata.UserPos = chk;
				tcpdata.DataDestination = "Chatting";
				login_frame.remove(login_frame.lobby_panel);
				Game_panel game_panel = new Game_panel(login_frame,ch,tcpdata,chk);
				login_frame.add(game_panel);
				login_frame.game_panelarr.add(game_panel);
				login_frame.repaint();
				break CHK;
			}if(chk>9) {
				break CHK;
			}
			chk++;
		}
		
	}

}
