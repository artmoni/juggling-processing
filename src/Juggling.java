
import java.util.ArrayList;

import processing.core.*;
import processing.serial.Serial;

public class Juggling extends PApplet {
	String SEPERATOR = "$";
	int MIN_Z = 30, MAX_Z = 200;
	int SPHERE_SIZE = 50;

	Serial myport;

	int defaultValueX, defaultValueY, defaultValueZ = 80;
	int x, y, z = defaultValueZ;
	int aX, aY, aZ;
	int id;

	// ArrayList firewors;
	// Firewor firewor_defaut;
	// Firewor fire;
	// PVector[] lim = new PVector[2];

	ArrayList<Sphere> spheres = new ArrayList();
	boolean test = false;

	int lastxtmp = defaultValueX;
	int lastytmp = defaultValueY;
	int lastztmp = defaultValueZ;

	public static void main(String[] args) {

		// We need do to from Processing 3
		String[] pArgs = { "Juggling " };
		Juggling j = new Juggling();
		PApplet.runSketch(pArgs, j);

	}

	public void settings() {
		myport = new Serial(this, "/dev/ttyACM0", 9600);
		size(displayWidth, displayHeight, P3D);
		defaultValueX = displayWidth / 2;
		defaultValueY = displayHeight / 2;
		x = defaultValueX;
		y = defaultValueY;
		

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
		background(0);

	}

		public void draw() {

		if (spheres.size() > 0)
			background(0, 0);

		String buffer = myport.readString();
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
				Sphere sphere = new Sphere(SPHERE_SIZE, id, x, y, z, this);
				spheres.add(sphere);
				currentSphere = sphere;
			}

			updateSphereValue(currentSphere, val);

		}
	}

	public void checkcollision(Sphere sphere) {
		if (sphere.checkCollision(x, y)) {
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
			sphere.setX(lastxtmp);
			sphere.setY(lastytmp);
			sphere.setZ(lastztmp);
			sphere.display();
		} else {
			// firewors.clear();
			sphere.display();
			// if (val[3] >= 3) {
			// fire = new Firewor(4000, new PVector(0, 0), new PVector(0, 100), -1,
			// color(200, 0, 50));
			// fire.display();
			// }
		}
	}

	public void updateSphereValue(Sphere sphere, float[] val) {
		if (val.length <= 3)
			return;
		if (x <= (displayWidth - SPHERE_SIZE * 2) && x > SPHERE_SIZE * 2 && y < (displayHeight - SPHERE_SIZE * 2)
				&& y > SPHERE_SIZE * 2) {
			lastxtmp = x;
			lastytmp = y;
		}
		int xtmp = x + (parseInt((round(val[1] * 20))));
		int ytmp = (y - (parseInt(round(val[2] * 20))));
		int ztmp = (z + (parseInt(round(val[3] * 100))));
		// int axtmp = aX+(int((round(val[4]*10))));
		// int aytmp = aY+(int((round(val[5]*10))));
		// int aztmp = aZ+(int((round(val[6]*10))));

		if (x != xtmp && xtmp >= sphere.getSize() / 2 && xtmp < displayWidth)
			x = xtmp;
		if (y != ytmp && ytmp >= 0 && ytmp < displayHeight)
			y = ytmp;
		if (ztmp > -200 && ztmp < 200)
			z = ztmp;

		sphere.setX(x);
		sphere.setY(y);
		sphere.setZ(z);
	}
}
