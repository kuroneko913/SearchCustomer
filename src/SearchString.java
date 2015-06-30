
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SearchString extends JPanel {
	private static final long serialVersionUID = 1L;
	private static JTextField pref = new JTextField("県");
	private static JTextField city = new JTextField("市区");
	//private static JTextField town = new JTextField("町");
	private static JTextField key = new JTextField("検索ワード");
	private static JButton find = new JButton("検索");
	
	SearchString() {
		
		pref.setBounds(0, 0, 160, 30);
		pref.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
		pref.addFocusListener(new PlaceholderFocusListener(pref));
		
		city.setBounds(165, 0, 160, 30);
		city.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
		city.addFocusListener(new PlaceholderFocusListener(city));
		
		//town.setBounds(330, 0, 160, 30);
		//town.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
		//town.addFocusListener(new PlaceholderFocusListener(town));
		
		key.setBounds(0, 40, 325, 30);
		key.setFont(new Font("MS ゴシック", Font.PLAIN, 16));
		key.addFocusListener(new PlaceholderFocusListener(key));
		
		find.setBounds(330, 0, 75, 30);
		find.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String p = pref.getText(), c = city.getText(), k = key.getText();// t = town.getText(), 
				p = DeleteSpace(p);
				c = DeleteSpace(c);
				//t = DeleteSpace(t);
				k = DeleteSpace(k);
				if(ErrorArgument(p, c,/* t,*/ k)) {
					new throwMessage<String>("入力項目に誤りがあります。");
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							find.setEnabled(false);
						}
					});
					
					Searching.place =new defineType();
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							MessagePane.setText("");
						}
					});
					makeSearchString getSearchString = new makeSearchString(p, c/*, t*/);
					getSearchString.start();
					try {
						getSearchString.join();
					} catch (InterruptedException e1) {
						
					} 
					
					if(getSearchString.getStatus()) {
						Searching get = new Searching(k, c);
						get.start();
					} else {
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								find.setEnabled(true);
							}
						});
					}

					initializeApp();
				} 
			}
		});
		
		this.setLayout(null);
		this.setBounds(10, 10, 490, 75);
		this.add(pref);
		this.add(city);
		//this.add(town);
		this.add(key);
		this.add(find);
	}
	
	private void initializeApp() {
		ParseJson.setCount(1);
	}
	
	public static JButton getFindButton() {
		return find;
	}
	
	private String DeleteSpace(String str) {
		
		if(str.equals("市区")) return "";
		str = str.replace("　", " "); // 全角 → 半角
		str = str.replace("	", " "); // tab　→　半角 （一応）
		return str;
	}
	
	private boolean ErrorArgument(String pref, String city, /*String town,*/ String key) {
		
		if(pref.equals("県")|| key.equals("検索ワード")) {
			return true;
		}
		/*else if(town.length() > 0 && city.length() == 0) {
			return true;
		} */
		else return false;
	}
}
