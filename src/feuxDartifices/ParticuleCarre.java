package feuxDartifices;

import processing.core.*;

public class ParticuleCarre extends Particule  {

	public ParticuleCarre(PVector pv,int color,PApplet parent,float vX,float vY) {
		this.pVector = pv.copy();
		this.color = color;
		this.parent = parent;
		this.vitesseX = vY;
		this.vitesseY = vY;
	}
	
	@Override
	protected PShape getShape() {
		return parent.createShape(PApplet.RECT, pVector.x, pVector.y, size, size);
	}

}
