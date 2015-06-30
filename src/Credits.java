
import java.awt.Font;
//import java.awt.GraphicsEnvironment;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Credits extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel google = new JLabel();
	private JLabel heartrails = new JLabel();
	
	Credits() {
		ClassLoader cl = this.getClass().getClassLoader();
		google.setIcon(new ImageIcon(cl.getResource("powered-by-google-on-white@2x.png")));
		google.setBounds(0, 45, 220, 30);
		
		heartrails.setText("HeartRails Geo API");
		heartrails.setFont(new Font("Arial", Font.BOLD, 18));
		heartrails.setBounds(10, 5, 220, 30);
		
		/** Cheak the usable fonts */
		/*GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String fontFamilyNames[] = ge.getAvailableFontFamilyNames();	
		 for(String name : fontFamilyNames) {
			 System.out.println(name);
		 }*/
			
		this.setLayout(null);
		this.setBounds(420, 5, 390, 80);
		this.add(google);
		this.add(heartrails);
	}
}
