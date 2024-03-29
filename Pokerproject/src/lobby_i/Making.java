package lobby_i;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.border.LineBorder;

import DB_p.ProfileDTO;
import game_p.Game_panel;
import lobby_p.Lobby;
import lobby_p.LobbyMain;
import login_p.Login_frame;
import net_p.MyData;
import net_p.Receiver;
import net_p.TCPData;

public class Making implements RoomAction {
	TCPData tcpdata;
	Lobby lobby;
	MyData myData;
	
	@Override
	public void room(Login_frame mainJf,Receiver ch,Lobby lobby, MyData myData,Integer addr) {
		
		this.lobby = lobby;
		String[] beting = {"1원","10원","100원","1000원"};
		JFrame jf = new JFrame();
		jf.setBounds(600, 200, 300, 200);
		jf.setLayout(null);
		jf.setResizable(false);
		JTextField title = new JTextField();
		title.setBounds(100, 35, 170, 25);
		JCheckBox pwchk = new JCheckBox();
		pwchk.setBounds(15, 70, 20, 20);
//		jf.add(pwchk);
		JPasswordField pw = new JPasswordField();
		pw.setEditable(false);
		pw.setBounds(100, 100, 170, 25);
		JComboBox<String> bet = new JComboBox<String>(beting);
		bet.setBackground(Color.white);
		bet.setBounds(100, 150, 170, 25);
//		jf.add(title);
//		jf.add(pw);
//		jf.add(bet);
		JLabel titleT = new JLabel("방 제목 : ");
		titleT.setBounds(20, 35, 60, 25);
		JLabel pwT = new JLabel("비밀번호 : ");
		pwT.setBounds(20, 100, 60, 25);
		JLabel betT = new JLabel("판돈 : ");
		betT.setBounds(20, 150, 60, 25);
		//jf.add(titleT);
		//jf.add(pwT);
		//jf.add(betT);
		
		JButton roomAdd = new JButton("만들기");
		roomAdd.setBounds(45, 40, 100, 60);
		roomAdd.setBorder(new LineBorder(Color.gray,3));
		roomAdd.setBackground(new Color(255,255,255));
		roomAdd.setFont(new Font("나눔고딕 Bold",Font.BOLD,20));
		pwchk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pwchk.isSelected()) {
					pw.setEditable(true);
				}else {
					pw.setEditable(false);					
				}
				
			}
		});
		
		roomAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mainJf.remove(lobby);
				Game_panel game_panel = new Game_panel(mainJf,ch,myData,addr);
				mainJf.add(game_panel);
				jf.setVisible(false);
				mainJf.repaint();
			}
		});
		JButton roomCancel = new JButton("취소");
		roomCancel.setBounds(150, 40, 100, 60);
		roomCancel.setBorder(new LineBorder(Color.gray,3));
		roomCancel.setFont(new Font("나눔고딕 Bold",Font.BOLD,20));
		roomCancel.setBackground(new Color(255,255,255));
		roomCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jf.setVisible(false);
			}
		});
		jf.add(roomAdd);
		jf.add(roomCancel);
		jf.setVisible(true);
		
	}
	
}
