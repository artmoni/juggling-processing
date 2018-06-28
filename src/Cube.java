
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

class Cube extends ObjectToDisplay {

	Cube(int d, String id, PVector pvector, PVector speed, PApplet parent, int color) {
		super(parent);
//		SIZE = d;
		this.id = id;
		this.pVector = pvector;
		this.speed = speed;
		this.color = color;
		this.ptsW = 30;
		this.ptsH = 30;
		initializeCube(ptsW, ptsH);
		img = parent.loadImage(texture[new Random().nextInt(texture.length)]);

	}

	protected PShape getShape() {
		return textureCube(200, 200, 200, img);
	}

	void initializeCube(int numPtsW, int numPtsH_2pi) {

		// The number of points around the width and height
		numPointsW = numPtsW + 1;
		numPointsH_2pi = numPtsH_2pi; // How many actual pts around the cube (not just from top to bottom)
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

	PShape textureCube(float rx, float ry, float rz, PImage t) {
		// These are so we can map certain parts of the image on to the shape
		PShape cube;
		cube = parent.createShape();
		cube.beginShape(PApplet.QUAD);
		// cube.textureMode(PApplet.NORMAL);
//		 cube.texture(img);
		cube.vertex(pVector.x, pVector.y, pVector.z, 0, 1);
		cube.vertex(pVector.x, pVector.y - 100, pVector.z, 1, 1);
		cube.vertex(pVector.x - 100, pVector.y - 100, pVector.z, 1, 0);
		cube.vertex(pVector.x - 100, pVector.y, pVector.z, 0, 0);

		cube.vertex(pVector.x, pVector.y, pVector.z);
		cube.vertex(pVector.x, pVector.y, pVector.z - 100);
		cube.vertex(pVector.x, pVector.y - 100, pVector.z - 100);
		cube.vertex(pVector.x, pVector.y - 100, pVector.z);

		cube.vertex(pVector.x - 100, pVector.y - 100, pVector.z);
		cube.vertex(pVector.x - 100, pVector.y - 100, pVector.z - 100);
		cube.vertex(pVector.x - 100, pVector.y, pVector.z - 100);
		cube.vertex(pVector.x - 100, pVector.y, pVector.z);

		cube.vertex(pVector.x - 100, pVector.y, pVector.z - 100);
		cube.vertex(pVector.x, pVector.y, pVector.z - 100);
		cube.vertex(pVector.x, pVector.y - 100, pVector.z - 100);
		cube.vertex(pVector.x - 100, pVector.y - 100, pVector.z - 100);

		cube.vertex(pVector.x - 100, pVector.y, pVector.z - 100);
		cube.vertex(pVector.x - 100, pVector.y, pVector.z);
		cube.vertex(pVector.x, pVector.y, pVector.z);
		cube.vertex(pVector.x, pVector.y, pVector.z - 100);

		cube.vertex(pVector.x, pVector.y - 100, pVector.z - 100);
		cube.vertex(pVector.x, pVector.y - 100, pVector.z);
		cube.vertex(pVector.x - 100, pVector.y - 100, pVector.z);
		cube.vertex(pVector.x - 100, pVector.y - 100, pVector.z - 100);
		cube.endShape(PApplet.CLOSE);
		return cube;
	}

}

// import java.util.ArrayList;
// import java.util.Random;
//
// import processing.core.PApplet;
// import processing.core.PImage;
// import processing.core.PShape;
// import processing.core.PVector;
//
// public class Cube extends ObjectToDisplay {
//
// Cube(int d, String id, PVector pvector, PVector speed, PApplet parent, int
// color) {
// size = d;
// this.id = id;
// this.pVector = pvector;
// this.speed = speed;
// this.parent = parent;
// this.color = color;
// img = parent.loadImage(texture[new Random().nextInt(texture.length)]);
//// // img = parent.loadImage("img/texture_strawberry.jpg");
//// // img = parent.loadImage("img/texture_strawberry.jpg");
//// // img = parent.loadImage("img/texture_strawberry.jpg");
//// // img = parent.loadImage("img/texture_strawberry.jpg");
//
//
// }
//
// @Override
// protected PShape getShape() {
// PShape cube;
// cube = parent.createShape();
// cube.beginShape(PApplet.QUAD);
//// cube.textureMode(PApplet.NORMAL);
//// cube.texture(img);
// cube.vertex(pVector.x, pVector.y, pVector.z,0,1);
// cube.vertex(pVector.x, pVector.y-100, pVector.z,1,1);
// cube.vertex(pVector.x-100, pVector.y-100, pVector.z,1,0);
// cube.vertex(pVector.x-100, pVector.y, pVector.z,0,0);
//
// cube.vertex(pVector.x, pVector.y, pVector.z);
// cube.vertex(pVector.x, pVector.y, pVector.z-100);
// cube.vertex(pVector.x, pVector.y-100, pVector.z-100);
// cube.vertex(pVector.x, pVector.y-100, pVector.z);
//
// cube.vertex(pVector.x-100, pVector.y-100, pVector.z);
// cube.vertex(pVector.x-100, pVector.y-100, pVector.z-100);
// cube.vertex(pVector.x-100, pVector.y, pVector.z-100);
// cube.vertex(pVector.x-100, pVector.y, pVector.z);
//
// cube.vertex(pVector.x-100, pVector.y, pVector.z-100);
// cube.vertex(pVector.x, pVector.y, pVector.z-100);
// cube.vertex(pVector.x, pVector.y-100, pVector.z-100);
// cube.vertex(pVector.x-100, pVector.y-100, pVector.z-100);
//
// cube.vertex(pVector.x-100, pVector.y, pVector.z-100);
// cube.vertex(pVector.x-100, pVector.y, pVector.z);
// cube.vertex(pVector.x, pVector.y, pVector.z);
// cube.vertex(pVector.x, pVector.y, pVector.z-100);
//
// cube.vertex(pVector.x, pVector.y-100, pVector.z-100);
// cube.vertex(pVector.x, pVector.y-100, pVector.z);
// cube.vertex(pVector.x-100, pVector.y-100, pVector.z);
// cube.vertex(pVector.x-100, pVector.y-100, pVector.z-100);
// cube.endShape(PApplet.CLOSE);
// return cube;
// }
//
// }
