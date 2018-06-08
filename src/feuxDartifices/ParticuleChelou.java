package feuxDartifices;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class ParticuleChelou extends Particule {

	PShape s;

	public ParticuleChelou(PVector pv, int color, PApplet parent, float vitesseX, float vitesseY) {
		this.pVector = pv.copy();
		this.color = color;
		this.parent = parent;
		this.vitesseX = vitesseX;
		this.vitesseY = vitesseY;
	}

	@Override
	protected PShape getShape() {
		s = parent.createShape(PApplet.GROUP);
		s.beginShape();
		PShape head, body,bottom;
		head = parent.createShape(PApplet.ELLIPSE, pVector.x, pVector.y, size, size);
		head.setFill(parent.color(255,255,0));
		body = parent.createShape(PApplet.RECT, pVector.x, pVector.y, size, size);
		body.setFill(parent.color(0,255,0));
		bottom = parent.createShape(PApplet.ELLIPSE, pVector.x+size, pVector.y+size, size, size);
		bottom.setFill(parent.color(0,0,255));
		s.addChild(head);
		s.addChild(body);
		s.addChild(bottom);
		return s;

	}

}
