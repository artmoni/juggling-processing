

import processing.core.PApplet;
import processing.core.PVector;

class Sphere 
{
	int size;
	int id;
	PVector pVector;
	PApplet parent;
	int color;

	Sphere() {
		size = 30;
	}
	Sphere(int d, int id, PVector pvector, PApplet parent,int color) {
		size = d;
		this.id = id;
		this.pVector = pvector;
		this.parent = parent;
		this.color = color;
	}

	void display() {

		parent.noStroke();

		parent.pushMatrix();
		parent.fill(color);
		parent.pointLight(51, 102, 126, this.pVector.x, this.pVector.y, this.pVector.y);

		parent.translate(this.pVector.x, this.pVector.y, this.pVector.z);
		parent.sphere(size);
		parent.popMatrix();

	}

	void displayCoord() {
		parent.textSize(20);
		parent.fill(color);
		parent.text("x = " + this.getPVector().x, this.pVector.x - 3 * size, this.pVector.y);
		parent.text("y = " + this.getPVector().y, this.pVector.x - 3 * size, this.pVector.y + 20);
		parent.text("z = " + this.getPVector().z, this.pVector.x - 3 * size, this.pVector.y + 40);
		parent.text("ID = " + this.getId(), this.pVector.x - 3 * size, this.pVector.y + 60);
	}

	boolean checkCollision(PVector vector) {
		// println("test collision -- x = "+x+" y = "+y+" --- W = "+displayWidth+" H =
		// "+displayHeight);
		if (vector.x > (parent.displayWidth - size * 2) || vector.x < size * 2) {
			return true;
		}
		if (vector.y > (parent.displayHeight - size * 2) || vector.y < size * 2) {
			return true;
		} else
			return false;
	}

	int getId() {
		return this.id;
	}
	//
	// float getPosX() {
	// return this.pVector.x;
	// }
	//
	// float getPosY() {
	// return this.pVector.y;
	// }
	//
	// float getPosZ() {
	// return this.pVector.z;
	// }

	float getSize() {
		return this.size;
	}

	PVector getPVector() {
		return this.pVector;
	}

	//
	// void setX(float x) {
	// this.pVector.x = x;
	// }
	//
	// void setY(float y) {
	// this.pVector.y = y;
	// }
	//
	// void setZ(float z) {
	// this.pVector.z = z;
	// }
	void setVector(PVector vector) {
		this.pVector = vector;
	}

	void destroy() {
		this.size = 0;
	}
}
