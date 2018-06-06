import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class sendData {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader("dataExemple.txt"));
		String line;
		while ((line = br.readLine()) != null) {
		   // process the line.
			System.out.println(line);
			Thread.sleep(100);
		}
		br.close();
	}

}
