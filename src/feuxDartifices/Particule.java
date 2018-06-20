package feuxDartifices;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public abstract class Particule {

	protected PVector pVector;
	protected int color;
	int size = 10;
	protected PApplet parent;
	protected float vitesseX;
	protected float vitesseY;
	PShape pShape;

	public boolean deplacer() {
		pVector.x += vitesseX;
		pVector.y += vitesseY;
		boolean isOutOfScreen = (pVector.x > parent.width || pVector.x < 0 || pVector.y > parent.height
				|| pVector.y < 0);
		return !isOutOfScreen;
	}

	public void display() {
		parent.fill(color);
		pShape = getShape();
		parent.translate(0, 0, 1000);
		parent.shape(pShape);
		// parent.ellipse(pVector.x, pVector.y, size, size);
	}

	protected abstract PShape getShape();

	public void gravity() {
		vitesseY += 0.5;
	}

	public void setPVector(PVector pv) {
		this.pVector = pv.copy();
	}

}