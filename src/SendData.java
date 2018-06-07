import java.io.BufferedReader;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SendData {
	private BufferedReader br = null;
	private String line;

	public void openFile() {
		try {
			this.br = new BufferedReader(new FileReader("dataExemple.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLine() {
		try {
				// process the line.
				return br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public void closeFile() {
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void read() {

			
		
	}
}
