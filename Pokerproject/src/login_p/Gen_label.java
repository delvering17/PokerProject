package login_p;

import javax.swing.JLabel;

public class Gen_label extends JLabel {
	
	public Gen_label(String name, int dis_x, int dis_y, int size_x, int size_y) {
		
		super(name);
		setBounds(dis_x, dis_y, size_x, size_y);
		
		
	}
	
}