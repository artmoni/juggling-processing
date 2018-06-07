
import java.util.ArrayList;

import processing.core.*;
import processing.serial.Serial;

public class Juggling extends PApplet {
	String SEPERATOR = "$";
	int MIN_Z = 30, MAX_Z = 200;
	int SPHERE_SIZE = 50;

	Serial myport;

	int defaultValueX, defaultValueY, defaultValueZ = 80;
	// int x, y, z = defaultValueZ;
	int aX, aY, aZ;
	int id;
	PVector pvector;

	// ArrayList firewors;
	// Firewor firewor_defaut;
	// Firewor fire;
	// PVector[] lim = new PVector[2];

	ArrayList<Sphere> spheres = new ArrayList();

	boolean test = false;

	float lastxtmp = defaultValueX;
	float lastytmp = defaultValueY;
	float lastztmp = defaultValueZ;
	
	
	SendData sendData = new SendData();

	public static void main(String[] args) {

		// We need do to from Processing 3
		String[] pArgs = { "Juggling " };
		Juggling j = new Juggling();
		PApplet.runSketch(pArgs, j);

	}

	public void settings() {

		// Init port for the Arduino data
		// myport = new Serial(this, "/dev/ttyACM0", 9600);
		
		size(displayWidth, displayHeight, P3D);
		defaultValueX = displayWidth / 2;
		defaultValueY = displayHeight / 2;
		// x = defaultValueX;
		// y = defaultValueY;
		pvector = new PVector(defaultValueX, defaultValueY, defaultValueZ);

		spheres.clear();

		// lim[0] = new PVector(-750, -1);
		// lim[1] = new PVector(750, 1000);
		// firewor_defaut = new Firewor(4000, new PVector(0, 0), new PVector(0, 10), -1,
		// color(200, 0, 50));
		// firewors = new ArrayList();
		// firewors.add(new Firewor(4000, new PVector(0, 0), new PVector(0, 10), -1,
		// color(200, 0, 50))); //ball_defaut);
	}

	public void setup() {
		sendData.openFile();
		background(0);

	}

	public void draw() {

		if (spheres.size() > 0)
			background(0, 0);

		// With the Arduino You need to init the port
		// String buffer = myport.readStringUntil('\n');

		// With the programme to read data on a txt file
		String buffer = sendData.getLine();
		if(buffer == null) {
			sendData.closeFile();
			sendData.openFile();
			buffer = sendData.getLine();
		}

		// Protocole start with # to know the id of the club
		if (buffer != null && buffer.startsWith("#")) {
			println(buffer);
			buffer = buffer.substring(1);
			float val[] = (parseFloat(split(buffer, SEPERATOR)));
			readDataAndUpdate(val);
		}

		for (Sphere mySphere : spheres) {
			// checkcollision(mySphere);
			mySphere.display();
			mySphere.displayCoord();
		}
		delay(100);
	}

	public void readDataAndUpdate(float[] val) {
		if (val != null) {
			Sphere currentSphere = null;

			int id = parseInt(val[0]);
			for (Sphere maSphere : spheres) {
				if (maSphere.getId() == id) {
					currentSphere = maSphere;
					break;
				}
			}
			if (!(currentSphere instanceof Sphere)) {
				Sphere sphere = new Sphere(SPHERE_SIZE, id, pvector, this);
				spheres.add(sphere);
				currentSphere = sphere;
			}

			updateSphereValue(currentSphere, val);

		}
	}

	public void checkcollision(Sphere sphere) {
		// if (sphere.checkCollision(sphere.getPVector()) {
		// Pulse pulse = new Pulse(defaultValueX, defaultValueY);
		// pulse.display();
		// float theta[]={0.0, 0.0, 0.0};
		// float m = x;
		// float my = y;
		// Vague [] v=new Vague[2];
		// smooth();
		// theta[0] += 0.02;
		// theta[1] += 0.03;
		// theta[2] += 0.01;
		// noStroke();
		// fill(0);
		// for (int j=0; j<2; j++) {
		// m=100;
		// my=60;
		// v[j]=new Vague(theta[j], j, m, my);
		// v[j].display(m, my);
		// }
		// PVector pVector = new PVector(lastxtmp,lastytmp,lastztmp);
		// sphere.setVector(pVector);
		// sphere.display();
		// } else {
		// firewors.clear();
		sphere.display();
		// if (val[3] >= 3) {
		// fire = new Firewor(4000, new PVector(0, 0), new PVector(0, 100), -1,
		// color(200, 0, 50));
		// fire.display();
		// }
	}
	// }

	public void updateSphereValue(Sphere sphere, float[] val) {
		if (val.length <= 3)
			return;
		if (sphere.getPVector().x <= (displayWidth - SPHERE_SIZE * 2) && sphere.getPVector().x > SPHERE_SIZE * 2
				&& sphere.getPVector().y < (displayHeight - SPHERE_SIZE * 2)
				&& sphere.getPVector().y > SPHERE_SIZE * 2) {

			lastxtmp = sphere.getPVector().x;
			lastytmp = sphere.getPVector().y;
		}
		PVector vtmp = new PVector(sphere.getPVector().x + (parseInt((round(val[1] * 20)))),
				(sphere.getPVector().y - (parseInt(round(val[2] * 20)))),
				(sphere.getPVector().z + (parseInt(round(val[3] * 100)))));

		float xtmp = sphere.getPVector().x + (parseInt((round(val[1] * 20))));
		float ytmp = (sphere.getPVector().y - (parseInt(round(val[2] * 20))));
		float ztmp = (sphere.getPVector().z + (parseInt(round(val[3] * 100))));
		// int axtmp = aX+(int((round(val[4]*10))));
		// int aytmp = aY+(int((round(val[5]*10))));
		// int aztmp = aZ+(int((round(val[6]*10))));

		if (sphere.getPVector().x != xtmp && xtmp >= sphere.getSize() / 2 && xtmp < displayWidth)
			sphere.getPVector().x = xtmp;
		if (sphere.getPVector().y != ytmp && ytmp >= 0 && ytmp < displayHeight)
			sphere.getPVector().y = ytmp;
		if (ztmp > -200 && ztmp < 200)
			sphere.getPVector().z = ztmp;
		PVector pVector = new PVector(sphere.getPVector().x, sphere.getPVector().y, sphere.getPVector().z);
		sphere.setVector(pVector);
	}
}
