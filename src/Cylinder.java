import processing.core.PApplet;
import processing.core.PShape;

public class Cylinder extends ObjectToDisplay{
	
	public Cylinder() {
		size = 30;
	}
	
	@Override
	protected PShape getShape() {
		// TODO Auto-generated method stub
		PShape cylinder = parent.createShape(PApplet.GROUP);
		  float angle = 360 / 52;
		  float halfHeight = size / 2;
		 
		  // draw top of the cylinder
		  PShape top = parent.createShape();
		  top.beginShape();
		  for (int i = 0; i < 52; i++) {
		    float x = PApplet.cos( PApplet.radians( i * angle ) ) * size;
		    float y = PApplet.sin( PApplet.radians( i * angle ) ) * size;
		    top.vertex( x, y, -halfHeight);
		  }
		  top.endShape(PApplet.CLOSE);
		  cylinder.addChild(top); //adds top to heirarchy
		 
		  // draw bottom of the cylinder
		  PShape bottom = parent.createShape();
		  bottom.beginShape();
		  for (int i = 0; i < 52; i++) {
		    float x = PApplet.cos( PApplet.radians( i * angle ) ) * size;
		    float y = PApplet.sin( PApplet.radians( i * angle ) ) * size;
		    bottom.vertex( x, y, halfHeight);
		  }
		  bottom.endShape(PApplet.CLOSE);
		  cylinder.addChild(bottom); //adds bottom to heirarchy
		 
		  // draw side of the cylinder
		  PShape side = parent.createShape();
		  side.beginShape(PApplet.QUAD_STRIP);
		  for (int i = 0; i < 52 + 1; i++) {
		    float x = PApplet.cos( PApplet.radians( i * angle ) ) * size;
		    float y = PApplet.sin( PApplet.radians( i * angle ) ) * size;
		    side.vertex( x, y, halfHeight);
		    side.vertex( x, y, -halfHeight);
		  }
		  side.endShape(PApplet.CLOSE);
		  cylinder.addChild(side); //adds side to heirarchy
		 
		  return cylinder;
	}
}
