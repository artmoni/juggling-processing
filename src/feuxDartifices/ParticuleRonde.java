package feuxDartifices;


import processing.core.*;

public class ParticuleRonde extends Particule  {

	public ParticuleRonde(PVector pv,int color,PApplet parent,float vitesseX,float vitesseY) {
		this.pVector = pv.copy();
		this.color = color;
		this.parent = parent;
		this.vitesseX = vitesseX;
		this.vitesseY = vitesseY;
	}

	@Override
	protected PShape getShape() {
		
		return parent.createShape(PApplet.ELLIPSE, pVector.x, pVector.y, size, size);
	}
}
