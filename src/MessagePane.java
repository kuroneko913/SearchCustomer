
import java.awt.Font;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

public class MessagePane extends JScrollPane {
	private static final long serialVersionUID = 1L; 
	private static JTextArea view = new JTextArea();
	
	MessagePane() {
		
		view.setSize(605, 500);
		view.setEditable(false);
		view.setFont(new Font("ÇlÇr ÉSÉVÉbÉN", Font.PLAIN, 24));
		view.setLineWrap(true);
		view.setWrapStyleWord(true);
		this.setViewportView(view);
		this.setBounds(5, 90, 605, 500);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public static void setText(String str) {
		view.setText(str);
	}
	
	public static void update(String str) {
		view.append(str);
		view.setCaretPosition(view.getDocument().getLength());
	}

}

class throwMessage<T> extends SwingWorker<Object, T> {
	private T message;
	
	throwMessage(T message) {
		this.message = message;
		this.execute();
	}
	
	@Override
	protected String doInBackground() {
		publish(message);
		return null;
	}

	@Override
    protected void process(List<T> chunks) {
        for(T str : chunks) {
            MessagePane.update(str.toString() + '\n');
        }
    }
}
