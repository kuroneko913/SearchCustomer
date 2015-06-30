import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;


public class Test {
	
	private static BufferedReader input;

	public static void main(String args[]) throws IOException {
		
		String line;
		File file = new File("Customers.txt");
		
		Set<Pair> shop = new HashSet<Pair>();
		input = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		while((line = input.readLine()) != null) {
			System.out.println(line);
			shop.add(new Pair(line.split(",")[1], line.split(",")[3]));
		}
		System.out.println(shop.size());
	}
}
