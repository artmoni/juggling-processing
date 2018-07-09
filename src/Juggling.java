
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;
import processing.serial.Serial;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import config.ConfigJugglingFromServer;

public class Juggling extends PApplet {
	int MIN_Z = 30, MAX_Z = 200;
	int OBJECT_SIZE = 50;

	Serial myport;

	HashMap<String, String> configFromServer = new HashMap<String, String>();
	ConfigJugglingFromServer config = null;

	int defaultValueX, defaultValueY, defaultValueZ = 80;
	// int x, y, z = defaultValueZ;

	// ArrayList firewors;
	// Firewor firewor_defaut;
	// Firewor fire;
	// PVector[] lim = new PVector[2];

	ArrayList<ObjectToDisplay> objects = new ArrayList<ObjectToDisplay>();

	boolean test = false;

	float lastxtmp = defaultValueX;
	float lastytmp = defaultValueY;
	float lastztmp = defaultValueZ;

	float defaultSpeed = 2;

	SampleData simpleData = new SampleData();
	String buffer;

	String url = "http://localhost:8000/scenes/current";
	String arduinoPort = "/dev/ttyACM0";

	// FeuxDArtificesControleur feuControlleur;

	Timer timer = new Timer();

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

		objects.clear();

		this.configFromServer.put("background", "70");

		// thread("retrieveDataFromServer");

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
			myport = new Serial(this, arduinoPort, 9600);
		} catch (Exception e) {
			simpleData.openFile();
		}
		// feuControlleur = new FeuxDArtificesControleur(this);

	}

	public void draw() {

		// box(width, height, 600);
		try {
			timer.schedule(new SynchroServerAction(), 1000);
		} catch (Exception e) {
			timer.cancel();
			config = new ConfigJugglingFromServer();
			config.setBackground(70);
			config.setForm("sphere");
			config.setVelocity(0);
		}

		if (objects.size() > 0)
			background(config.getBackground());

		if (myport != null) {
			// With the Arduino You need to init the port55
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

			ObjectToDisplay objectToDisplay = createObjectIfNotExist(protocole.getResourceId());
			updateObjectValue(objectToDisplay, protocole.getRessourceGyro());

			if (objects.size() > 1) {
				PVector eye = objects.get(0).getPVector();
				PVector whattosee = objects.get(1).getPVector();
				// camera(0, 0, 0, 0, 0, 0, whattosee.x, whattosee.y, whattosee.z);
				// camera(mouseX, height / 2, (height / 2) / tan(PI / 6), mouseX, height / 2,
				// tan(PI) * width, 0, 1, 0);
			}

			for (ObjectToDisplay myObject : objects) {
				// checkcollisionScreenEdge(myObject);

				ObjectToDisplay collision = myObject.collision(objects);
				if (collision instanceof ObjectToDisplay) {
					PVector ab = new PVector();
					ab.set(myObject.pVector);
					ab.sub(collision.pVector);
					ab.normalize();
					while (myObject.pVector.dist(collision.pVector) < (myObject.getSize() + collision.getSize()) * 4) {
						// *spring) {
						myObject.pVector.add(ab);
					}
					PVector n = PVector.sub(myObject.pVector, collision.pVector);
					n.normalize();
					PVector u = PVector.sub(myObject.pVector, collision.pVector);
					PVector un = u.mult(n.dot(u));

					u.sub(un);
					myObject.setVector(myObject.getPVector().sub(u));

					// myObject.speed = PVector.add(u, collision.speed);
					// collision.speed = PVector.add(un, collision.speed);
					// myObject.setVector(myObject.getPVector().copy().mult(-1));
					myObject.display();
					println("collision with " + collision.getId());

					Pulse pulse = new Pulse(50, 50, this);
					pulse.display();
				} else {
					myObject.display();
					myObject.displayCoord();

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		// feuControlleur.addFeu(new PVector(displayWidth / 2, displayHeight / 2, 50));
		// feuControlleur.display();
		delay(100);
	}

	public ObjectToDisplay createObjectIfNotExist(String id) {

		ObjectToDisplay currentObj = null;

		for (ObjectToDisplay monObjet : objects) {
			if (monObjet.getId().equals(id)) {
				currentObj = monObjet;
				break;
			}
		}
		if (!(currentObj instanceof ObjectToDisplay)) {
			PVector vector = new PVector(defaultValueX, defaultValueY, defaultValueZ);
			PVector speed = null;
			Random random = new Random();
			int red = random.nextInt(255) + 1;
			int blue = random.nextInt(255) + 1;
			int green = random.nextInt(255) + 1;
			int color = color(red, green, blue);
			ObjectToDisplay form;
			System.out.println(config.getForm());
			switch (config.getForm()) {
			case "cube":
				form = new Cube(OBJECT_SIZE, id, vector, speed, this, color);
				objects.add(form);
				currentObj = form;
				break;
			case "cylinder":
				form = new Cylinder(OBJECT_SIZE, id, vector, speed, this, color);
				objects.add(form);
				currentObj = form;
				break;
			default:
				form = new Sphere(OBJECT_SIZE, id, vector, speed, this, color);
				objects.add(form);
				currentObj = form;
				break;
			}
			// ObjectToDisplay form = new Cube(OBJECT_SIZE, id, vector, speed, this, color);
			// ObjectToDisplay form = new Cylinder(OBJECT_SIZE, id, vector, speed, this,
			// color);

		}

		return currentObj;

	}

	public void checkcollisionScreenEdge(ObjectToDisplay object) {
		if (object.checkCollision(object.getPVector(), object.getVitesse())) {
			System.out.println("collision bord");

			Pulse pulse = new Pulse(50, 50, this);
			pulse.display();

			// Pulse pulse = new Pulse(defaultValueX, defaultValueY, this);
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

			// PVector pVectorSpeed = new PVector(0, 0, 0);
			// PVector pVector = new PVector(lastxtmp, lastytmp, lastztmp);
			// object.setSpeed(pVectorSpeed);
			// object.setVector(pVector);

			// object.display();
			// } else {
			// firewors.clear();
			// object.display();
			// if (val[3] >= 3) {
			// fire = new Firewor(4000, new PVector(0, 0), new PVector(0, 100), -1,
			// color(200, 0, 50));
			// fire.display();

		}
	}
	// }

	// public void mousePressed() {
	// if (mousePressed) {
	// System.out.println("CLIC");
	// feuControlleur.addFeu(new PVector(mouseX, mouseY));
	// }
	// }

	public void updateObjectValue(ObjectToDisplay object, PVector gyroVector) {
		if (object.getPVector().x <= (displayWidth - OBJECT_SIZE * 2) && object.getPVector().x > OBJECT_SIZE * 2
				&& object.getPVector().y < (displayHeight - OBJECT_SIZE * 2)
				&& object.getPVector().y > OBJECT_SIZE * 2) {

			lastxtmp = object.getPVector().x;
			lastytmp = object.getPVector().y;
		}
		// PVector vtmp = new PVector(object.getPVector().x + (parseInt((round(val[1] *
		// 20)))),
		// (object.getPVector().y - (parseInt(round(val[2] * 20)))),
		// (object.getPVector().z + (parseInt(round(val[3] * 100)))));

		PVector lastVector = object.getPVector();
		PVector vtmp = object.getPVector().add(gyroVector);

		float x = object.getPVector().x * 20;
		float y = object.getPVector().y * 20;
		float z = object.getPVector().z * 100;

		if (object.getPVector().x != vtmp.x && vtmp.x >= object.getSize() / 2 && vtmp.x < displayWidth)
			x = vtmp.x;
		if (object.getPVector().y != vtmp.y && vtmp.y >= 0 && vtmp.y < displayHeight)
			y = vtmp.y;
		if (vtmp.z > -200 && vtmp.z < 200)
			z = vtmp.z;

		PVector pVector = new PVector(x, y, z);
		PVector speedVector = PVector.sub(pVector, lastVector);
		speedVector.setMag((float) 0.8);
		object.setVector(pVector);
		object.setSpeed(speedVector);
	}

	public void retrieveDataFromServer() {
		ObjectMapper mapper = new ObjectMapper();

		JSONObject json = loadJSONObject(url);

		// this.configFromServer.put("background", "" + json.get("background"));
		// this.configFromServer.put("form", "" + json.get("form"));
		// this.configFromServer.put("velocity", "" + json.get("velocity"));

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try {
			config = mapper.readValue(json.toString(), ConfigJugglingFromServer.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("background : " + config.getBackground());
		// System.out.println("Form : " + config.getForm());
		// System.out.println("velocity : " + config.getVelocity());

		// this.configFromServer.put("background", "" + json.getInt("background"));
		// back = json.getInt("background");
	}

	class SynchroServerAction extends TimerTask {

		public void run() {
			retrieveDataFromServer();
			// System.out.println("Terminé!");
		}
	}
}
