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
import net_p.MyData;
import net_p.Receiver;
import net_p.TCPData;


public interface RoomAction {


	void room(Login_frame mainJf, Receiver ch,Lobby lobby, TCPData data,MyData myData,Integer addr);
}
