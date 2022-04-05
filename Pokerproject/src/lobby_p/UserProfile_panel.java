package lobby_p;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import DB_p.ProfileDTO;

public class UserProfile_panel extends JPanel{

	String nickname;
	String gender;
	String introduce = "";
	int totalGame;
	int win;
	int lose;
	long userMoney;
	
	JLabel logo_text;
	JLabel nickname_text;
	JLabel gender_text;
	JLabel totalGame_text;
	JLabel win_text;
	JLabel lose_text;
	JLabel userMoney_text;
	JLabel introducelogo_text;
	JTextArea introduce_text;
	
	
	
	public UserProfile_panel(ProfileDTO profileDTO) {
		this.nickname = profileDTO.nickname;
		this.gender = profileDTO.gender;
		
		this.totalGame = profileDTO.totalGame;
		this.win = profileDTO.win;
		this.lose = profileDTO.lose;
		this.userMoney = profileDTO.money;
		
		String str = profileDTO.introduce;

		char[] arr = str.toCharArray();
		
		for (int i = 0 ; i < arr.length ; i++ ) {
			    this.introduce += arr[i];
			if (i !=0 && i % 25 == 0) {
				
				this.introduce += "\n";
		
			}
		}
		
		
		
		
		
		
		setBounds(820,10 , 350, 500);
		setLayout(null);
		setBackground(Color.red);
		logo_text = new JLabel();
		logo_text.setBounds(150,0,200,50); 
		logo_text.setText("프로필");
		
		nickname_text = new JLabel();
		nickname_text.setBounds(0,50,200,50); 
		nickname_text.setText("닉네임 : " + nickname);
		
		gender_text = new JLabel();
		gender_text.setBounds(0,100,200,50);  
		gender_text.setText("성별 : " + gender);
		
		totalGame_text = new JLabel();
		totalGame_text.setBounds(0,150,200,50); 
		totalGame_text.setText("전적 : " + totalGame);
		
		win_text = new JLabel();
		win_text.setBounds(0,200,200,50);  
		win_text.setText("승 : " + win);
		
		lose_text = new JLabel();
		lose_text.setBounds(0,250,200,50); 
		lose_text.setText("패 : " + lose);
		
		userMoney_text = new JLabel();
		userMoney_text.setBounds(0,300,200,50); 
		userMoney_text.setText("보유머니 : " + userMoney);
		
		introducelogo_text = new JLabel();
		introducelogo_text.setBounds(150,350,100,50);
		introducelogo_text.setText("소개");
		
		introduce_text = new JTextArea();
		introduce_text.setBounds(0,400,350,150);
		introduce_text.setText(introduce);
		introduce_text.setEnabled(false);
		
		add(logo_text);
		add(nickname_text);
		add(gender_text);
		add(totalGame_text);
		add(win_text);
		add(lose_text);
		add(userMoney_text);
		add(introducelogo_text);
		add(introduce_text);
	
		
	}
	
	
	
}

class TE extends JFrame {
	
	public TE() {
		
		setBounds(50,50,1200,800);
		setLayout(null);
		
		add(new UserProfile_panel(new ProfileDTO()));
		
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	public static void main(String[] args) {
		
		new TE();
		
	}
	
}
