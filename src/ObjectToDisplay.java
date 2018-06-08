import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

public abstract class ObjectToDisplay {

	protected int size;
	protected int id;
	protected PVector pVector;
	protected PVector vitesse;
	protected PApplet parent;
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

	protected void display() {

		parent.noStroke();

		parent.pushMatrix();
		parent.fill(color);

		parent.translate(this.pVector.x, this.pVector.y, this.pVector.z);
		// parent.sphere(size);
		parent.rotate(this.pVector.dist(new PVector(0, 0, 0)) * 10, this.pVector.x, this.pVector.y, this.pVector.y);
		pShape = getShape();
		parent.shape(pShape);
		parent.popMatrix();

	}

	protected void displayCoord() {
		parent.textSize(20);
		parent.fill(color);
		parent.text("x = " + this.getPVector().x, this.pVector.x - 3 * size, this.pVector.y, this.pVector.z);
		parent.text("y = " + this.getPVector().y, this.pVector.x - 3 * size, this.pVector.y + 20, this.pVector.z);
		parent.text("z = " + this.getPVector().z, this.pVector.x - 3 * size, this.pVector.y + 40, this.pVector.z);
		parent.text("ID = " + this.getId(), this.pVector.x - 3 * size, this.pVector.y + 60, this.pVector.z);
	}

	boolean checkCollision(PVector vector) {
		if (vector.x > (parent.displayWidth - size * 2) || vector.x < size * 2) {
			return true;
		}
		if (vector.y > (parent.displayHeight - size * 2) || vector.y < size * 2) {
			return true;
		} else
			return false;
	}

	protected int getId() {
		return this.id;
	}

	protected float getSize() {
		return this.size;
	}

	protected PVector getPVector() {
		return this.pVector;
	}

	protected void setVector(PVector vector) {
		this.pVector = vector;
	}

	protected abstract PShape getShape();

}