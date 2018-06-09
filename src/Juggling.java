
import java.util.ArrayList;
import java.util.Random;

import feuxDartifices.*;
import processing.core.*;
import processing.serial.Serial;

public class Juggling extends PApplet {
	int MIN_Z = 30, MAX_Z = 200;
	int SPHERE_SIZE = 50;

	Serial myport;

	int defaultValueX, defaultValueY, defaultValueZ = 80;
	// int x, y, z = defaultValueZ;

	// ArrayList firewors;
	// Firewor firewor_defaut;
	// Firewor fire;
	// PVector[] lim = new PVector[2];

	ArrayList<Sphere> spheres = new ArrayList<Sphere>();

	boolean test = false;

	float lastxtmp = defaultValueX;
	float lastytmp = defaultValueY;
	float lastztmp = defaultValueZ;

	float defaultVitesse = 2;

	SampleData simpleData = new SampleData();
	String buffer;

	FeuxDArtificesControleur feux;
	ArrayList<FeuDartifice> listeFeux = new ArrayList<>();

	public static void main(String[] args) {

		// We need do to from Processing 3
		String[] pArgs = { "Juggling " };
		Juggling j = new Juggling();
		PApplet.runSketch(pArgs, j);

	}

	public void settings() {

		// Init port for the Arduino data

		size(displayWidth, displayHeight, P3D);
		defaultValueX = displayWidth / 2;
		defaultValueY = displayHeight / 2;
		// x = defaultValueX;
		// y = defaultValueY;

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
		try {
			myport = new Serial(this, "/dev/ttyACM0", 9600);
		} catch (Exception e) {
			simpleData.openFile();
		}
		background(0);
		feux = new FeuxDArtificesControleur(this);

	}

	public void draw() {

		if (spheres.size() > 0)
			background(255);

		if (myport != null) {
			// With the Arduino You need to init the port
			buffer = myport.readStringUntil('\n');
		} else {
			// With the programme to read data on a txt file
			buffer = simpleData.getLine();
			if (buffer == null) {
				simpleData.closeFile();
				simpleData.openFile();
				buffer = simpleData.getLine();
			}
		}
		try {
			DataProtocole protocole = new DataProtocole(buffer);

			ObjectToDisplay objectToDisplay = createSphereIfNotExist(protocole.getResourceId());
			updateSphereValue(objectToDisplay, protocole.getRessourceGyro(), protocole.getRessourceSpeed());

			if (spheres.size() > 1) {
				PVector eye = spheres.get(0).getPVector();
				PVector whattosee = spheres.get(1).getPVector();
				// camera(0, 0, 0, 0, 0, 0, whattosee.x, whattosee.y, whattosee.z);
				// camera(mouseX, height / 2, (height / 2) / tan(PI / 6), mouseX, height / 2,
				// tan(PI) * width, 0, 1, 0);
			}

			for (Sphere mySphere : spheres) {
				// checkcollision(mySphere);

				Sphere collision = mySphere.collision(spheres);
				if (collision instanceof Sphere) {
					PVector ab = new PVector();
					ab.set(mySphere.pVector);
					ab.sub(collision.pVector);
					ab.normalize();
					while (mySphere.pVector.dist(collision.pVector) < (mySphere.getSize() + collision.getSize()) * 4) {
						// *spring) {
						mySphere.pVector.add(ab);
					}
					PVector n = PVector.sub(mySphere.pVector, collision.pVector);
					n.normalize();
					PVector u = PVector.sub(mySphere.pVector, collision.pVector);
					PVector un = u.mult(n.dot(u));

					u.sub(un);
					mySphere.setVector(mySphere.getPVector().sub(u));
					// mySphere.speed = PVector.add(u, collision.speed);
					// collision.speed = PVector.add(un, collision.speed);
					// mySphere.setVector(mySphere.getPVector().copy().mult(-1));
					mySphere.display();
					println("collision with " + collision.getId());
				} else {
					mySphere.display();
					mySphere.displayCoord();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		feux.display();
		delay(100);
	}

	public ObjectToDisplay createSphereIfNotExist(String id) {

		ObjectToDisplay currentSphere = null;

		for (ObjectToDisplay maSphere : spheres) {
			if (maSphere.getId().equals(id)) {
				currentSphere = maSphere;
				break;
			}
		}
		if (!(currentSphere instanceof Sphere)) {
			PVector vector = new PVector(defaultValueX, defaultValueY, defaultValueZ);
			PVector vitesse = null;
			Random random = new Random();
			int red = random.nextInt(255) + 1;
			int blue = random.nextInt(255) + 1;
			int green = random.nextInt(255) + 1;
			int color = color(red, green, blue);
			Sphere sphere = new Sphere(SPHERE_SIZE, id, vector, vitesse, this, color);
			spheres.add(sphere);
			currentSphere = sphere;
		}

		return currentSphere;

	}

	public void checkcollision(ObjectToDisplay sphere) {
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
		// sphere.display();
		// if (val[3] >= 3) {
		// fire = new Firewor(4000, new PVector(0, 0), new PVector(0, 100), -1,
		// color(200, 0, 50));
		// fire.display();
		// }
	}
	// }

	public void updateSphereValue(ObjectToDisplay sphere, PVector gyroVector, PVector speedVector) {
		if (sphere.getPVector().x <= (displayWidth - SPHERE_SIZE * 2) && sphere.getPVector().x > SPHERE_SIZE * 2
				&& sphere.getPVector().y < (displayHeight - SPHERE_SIZE * 2)
				&& sphere.getPVector().y > SPHERE_SIZE * 2) {

			lastxtmp = sphere.getPVector().x;
			lastytmp = sphere.getPVector().y;
		}
		// PVector vtmp = new PVector(sphere.getPVector().x + (parseInt((round(val[1] *
		// 20)))),
		// (sphere.getPVector().y - (parseInt(round(val[2] * 20)))),
		// (sphere.getPVector().z + (parseInt(round(val[3] * 100)))));

		PVector vtmp = sphere.getPVector().add(gyroVector);

		if (sphere.getPVector().x != vtmp.x && vtmp.x >= sphere.getSize() / 2 && vtmp.x < displayWidth)
			sphere.getPVector().x = vtmp.x;
		if (sphere.getPVector().y != vtmp.y && vtmp.y >= 0 && vtmp.y < displayHeight)
			sphere.getPVector().y = vtmp.y;
		if (vtmp.z > -200 && vtmp.z < 200)
			sphere.getPVector().z = vtmp.z;

		PVector pVector = new PVector(sphere.getPVector().x, sphere.getPVector().y, sphere.getPVector().z);
		sphere.setVector(pVector);
		sphere.setVitesse(speedVector);
	}

}
