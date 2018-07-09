import java.util.ArrayList;
import java.util.Random;

import processing.core.*;

public abstract class ObjectToDisplay {

	protected PApplet parent;

	protected int SIZE = 50;

	protected String id;
	protected PVector pVector;
	protected PVector speed;
	protected int color;
	protected int ptsW;
	protected int ptsH;
	int numPointsW;
	int numPointsH_2pi;
	int numPointsH;
	float[] coorX;
	float[] coorY;
	float[] coorZ;
	float[] multXZ;
	protected PImage img;
	protected String texture[] = { "img/texture_earth.jpg", "img/planet_pluto.jpg", "img/planet_pluto2.jpg",
			"img/planet_miranda.jpg", "img/texture_adidas.jpg", "img/texture_foot.jpg", "img/texture_football.png",
			"img/texture_strawberry.jpg", };
	protected PShape pShape;

	public ObjectToDisplay(PApplet parent) {
		this.parent = parent;
		this.id = String.valueOf(new Random().nextInt(65000));
		this.pVector = new PVector(parent.width / 2, parent.height / 2, 0);
		this.speed = new PVector(0, 0);
		this.ptsW = 30;
		this.ptsH = 30;
	}

	protected void display() {

		parent.noStroke();

		parent.pushMatrix();
		parent.fill(color);

		this.pVector.add(speed.mult(2));
		parent.translate(this.pVector.x, this.pVector.y, this.pVector.z);
		// parent.sphere(size);
		// this.pVector.y, this.pVector.y);
		pShape = getShape();
		parent.rotateX((float)(parent.random((float)(10))));
		parent.rotateY((float)(parent.random((float)(10))));
		parent.rotateZ((float)(parent.random((float)(10))));
		parent.shape(pShape);
		parent.popMatrix();

	}

	protected void displayCoord() {
		parent.textSize(20);
		parent.fill(color);
		parent.text("x = " + this.getPVector().x, this.pVector.x - 3 * SIZE, this.pVector.y, this.pVector.z);
		parent.text("y = " + this.getPVector().y, this.pVector.x - 3 * SIZE, this.pVector.y + 20, this.pVector.z);
		parent.text("z = " + this.getPVector().z, this.pVector.x - 3 * SIZE, this.pVector.y + 40, this.pVector.z);
		parent.text("ID = " + this.getId(), this.pVector.x - 3 * SIZE, this.pVector.y + 60, this.pVector.z);
		parent.text("vitesse = " + this.getVitesse(), this.pVector.x - 3 * SIZE, this.pVector.y + 80, this.pVector.z);
	}

	protected String getId() {
		return this.id;
	}

	protected float getSize() {
		return this.SIZE;
	}

	protected PVector getPVector() {
		return this.pVector;
	}

	protected PVector getVitesse() {
		return this.speed;
	}

	protected void setVector(PVector vector) {
		this.pVector = vector;
	}

	protected void setSpeed(PVector vector) {
		this.speed = vector;
	}

	public ObjectToDisplay collision(ArrayList<ObjectToDisplay> objects) {
		ObjectToDisplay objA = this;
		for (int j = 0; j < objects.size(); j++) {
			ObjectToDisplay objB = objects.get(j);
			if (objA.getId() != objB.getId() && objA.pVector.dist(objB.pVector) < (objA.SIZE + objB.SIZE) * 2) {
				// bounce(ballA, ballB);
				return objB;
			}
		}
		return null;
	}

	public boolean checkCollision(PVector _location, PVector speed) {
		PVector location = _location.copy();
		location.add(speed);
		if (location.x > (parent.width - SIZE * 2) || location.x < SIZE * 2) {
			System.out.println("Collision X");
			return true;
		}
		if (location.y > (parent.height) || location.y < SIZE * 2) {
			System.out.println("Collision Y");
			System.out.println("Y = " + location.y);
			System.out.println("SIZE Y = " + parent.height);
			return true;
		}
		// if (vector.z > 0) {
		// return true;
		// } else
		return false;
	}

	protected abstract PShape getShape();

}