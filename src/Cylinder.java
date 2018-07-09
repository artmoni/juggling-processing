import java.util.Random;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Cylinder extends ObjectToDisplay {

	public Cylinder(PApplet parent) {
		super(parent);
	}

	Cylinder(int d, String id, PVector pvector, PVector speed, PApplet parent, int color) {
		super(parent);
		// SIZE = d;
		this.id = id;
		this.pVector = pvector;
		this.speed = speed;
		this.color = color;
		this.ptsW = 30;
		this.ptsH = 30;
		initializeCylinder(ptsW, ptsH);
		img = parent.loadImage(texture[new Random().nextInt(texture.length)]);

	}

	void initializeCylinder(int numPtsW, int numPtsH_2pi) {

		// The number of points around the width and height
		numPointsW = numPtsW + 1;
		numPointsH_2pi = numPtsH_2pi; // How many actual pts around the sphere (not just from top to bottom)
		numPointsH = PApplet.ceil((float) numPointsH_2pi / 2) + 1; // How many pts from top to bottom (abs(....) b/c of
																	// the possibility of an odd numPointsH_2pi)

		coorX = new float[numPointsW]; // All the x-coor in a horizontal circle radius 1
		coorY = new float[numPointsH]; // All the y-coor in a vertical circle radius 1
		coorZ = new float[numPointsW]; // All the z-coor in a horizontal circle radius 1
		multXZ = new float[numPointsH]; // The radius of each horizontal circle (that you will multiply with coorX and
										// coorZ)

		for (int i = 0; i < numPointsW; i++) { // For all the points around the width
			float thetaW = i * 2 * PApplet.PI / (numPointsW - 1);
			coorX[i] = PApplet.sin(thetaW);
			coorZ[i] = PApplet.cos(thetaW);
		}

		for (int i = 0; i < numPointsH; i++) { // For all points from top to bottom
			if ((int) (numPointsH_2pi / 2) != (float) numPointsH_2pi / 2 && i == numPointsH - 1) { // If the
																									// numPointsH_2pi is
																									// odd and it is at
																									// the last pt
				float thetaH = (i - 1) * 2 * PApplet.PI / (numPointsH_2pi);
				coorY[i] = PApplet.cos(PApplet.PI + thetaH);
				multXZ[i] = 0;
			} else {
				// The numPointsH_2pi and 2 below allows there to be a flat bottom if the
				// numPointsH is odd
				float thetaH = i * 2 * PApplet.PI / (numPointsH_2pi);

				// PI+ below makes the top always the point instead of the bottom.
				coorY[i] = PApplet.cos(PApplet.PI + thetaH);
				multXZ[i] = PApplet.sin(thetaH);
			}
		}
	}

	@Override
	protected PShape getShape() {
		// TODO Auto-generated method stub
		PShape cylinder = parent.createShape(PApplet.GROUP);
		float angle = 360 / 25;
		float halfHeight = 50 / 2;

		// draw top shape
		PShape top = parent.createShape();
		top.beginShape();
		for (int i = 0; i < 26; i++) {
			float x = PApplet.cos(PApplet.radians(i * angle)) * this.SIZE;
			float y = PApplet.sin(PApplet.radians(i * angle)) * this.SIZE;
			top.vertex(x, y, -halfHeight);
		}
		top.endShape(PApplet.CLOSE);
		cylinder.addChild(top);
		// draw bottom shape
		PShape bottom = parent.createShape();
		bottom.beginShape();
		for (int i = 0; i < 26; i++) {
			float x = PApplet.cos(PApplet.radians(i * angle)) * this.SIZE;
			float y = PApplet.sin(PApplet.radians(i * angle)) * this.SIZE;
			bottom.vertex(x, y, halfHeight);
		}
		bottom.endShape(PApplet.CLOSE);
		cylinder.addChild(bottom);

		PShape body = parent.createShape();
		 // draw body
		body.beginShape(PApplet.TRIANGLE_STRIP);
		for (int i = 0; i < 26 + 1; i++) {
		    float x = PApplet.cos( PApplet.radians( i * angle ) ) * this.SIZE;
		    float y = PApplet.sin( PApplet.radians( i * angle ) ) * this.SIZE;
		    body.vertex( x, y, halfHeight);
		    body.vertex( x, y, -halfHeight);    
		}
		body.endShape(PApplet.CLOSE);
		cylinder.addChild(body);
		return cylinder;
	}
}
