import processing.core.PApplet;
import processing.core.PVector;

/*
 * Data sending by the Arduino to analyse
 * #40473$0.049$0.000$0.026$0.848$0.641$9.80
 * 
 */
public class DataProtocole {

	String SEPERATOR = "$";
	String stringToAnnalyse;
	String values[];

	public DataProtocole(String str) {
		// TODO Auto-generated constructor stub
		this.stringToAnnalyse = str;
		this.explode();
	}

	private void explode() {
		// Protocole start with # to know the id of the club
		if (stringToAnnalyse != null && stringToAnnalyse.startsWith("#")) {
			System.out.println(stringToAnnalyse);
			stringToAnnalyse = stringToAnnalyse.substring(1);
			values = PApplet.split(stringToAnnalyse, SEPERATOR);
		}
	}

	public String getResourceId() {
		return values[0];
	}

	public PVector getRessourceGyro() {
		return new PVector(PApplet.parseFloat(values[1]), PApplet.parseFloat(values[2]),
				PApplet.parseFloat(values[3]));
	}

	public PVector getRessourceSpeed() {
		return new PVector(PApplet.parseFloat(values[4]), PApplet.parseFloat(values[5]),
				PApplet.parseFloat(values[6]));
	}

}
