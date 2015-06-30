
import javax.swing.JFrame;

public class MainApp extends JFrame {
	private static final long serialVersionUID = 1L;

	MainApp() {
		
		this.getContentPane().setLayout(null);
		this.setTitle("Find Customer");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(640, 650);
		this.setLocation(100, 100);
		this.setResizable(false);
		
		this.getContentPane().add(new Credits());
		this.getContentPane().add(new StopSearching());
		this.getContentPane().add(new SearchString());
		this.getContentPane().add(new MessagePane());
		
		new throwMessage<String>("県、市単位での顧客を探します。\n市を指定する際には検索結果の精度を上げるため、県の指定もしてください。\n" + 
				"検索する前に一度Google Mapで望むような結果が返ってくることを確認してから、検索開始することを強く推奨します。");
		
	
		this.setVisible(true);
	}
	
	public static void main(String argv[]){
		new MainApp();
	}
}
