import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class StopSearching extends JButton {
	static final long serialVersionUID = 1L;
	/** 
	 * @stop
	 * true 	:	non-stopping
	 * false 	:	stopping	
	 */
	
	StopSearching() {
		this.setText("’†Ž~");
		this.setBounds(340, 50, 75, 30);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(getGoogleMapJson.getjson != null)
					getGoogleMapJson.getjson.setStatus(false);
			}
		});
	}
}
