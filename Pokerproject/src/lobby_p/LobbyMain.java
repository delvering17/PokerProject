package lobby_p;

import java.awt.Graphics;

import javax.swing.JFrame;

public class LobbyMain extends JFrame{
	LobbyMain test;
	public LobbyMain() {
		setLayout(null);
		test = this;
		setBounds(200, 100, 1200, 800);
		add(new Lobby(this));
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	class repaintThread extends Thread {
		@Override
		public void run() {
			test.repaint();
		}
	}
	public static void main(String[] args) {
		new LobbyMain();
	}

}