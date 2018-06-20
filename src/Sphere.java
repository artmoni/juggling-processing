
import java.util.ArrayList;
import java.util.Random;

import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PShape;
import processing.core.PVector;

class Sphere extends ObjectToDisplay {
	Sphere() {
		size = 30;
	}

	Sphere(int d, String id, PVector pvector, PVector speed, PApplet parent, int color) {
		size = d;
		this.id = id;
		this.pVector = pvector;
		this.speed = speed;
		this.parent = parent;
		this.color = color;
		this.ptsW = 30;
		this.ptsH = 30;
		initializeSphere(ptsW, ptsH);
		img = parent.loadImage(texture[new Random().nextInt(texture.length)]);
		// img = parent.loadImage("img/texture_strawberry.jpg");
		// img = parent.loadImage("img/texture_strawberry.jpg");
		// img = parent.loadImage("img/texture_strawberry.jpg");
		// img = parent.loadImage("img/texture_strawberry.jpg");

	}

	protected PShape getShape() {
		return textureSphere(200, 200, 200, img);
	}

	void initializeSphere(int numPtsW, int numPtsH_2pi) {

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

	PShape textureSphere(float rx, float ry, float rz, PImage t) {
		// These are so we can map certain parts of the image on to the shape
		float changeU = t.width / (float) (numPointsW - 1);
		float changeV = t.height / (float) (numPointsH - 1);
		float u = 0; // Width variable for the texture
		float v = 0; // Height variable for the texture
		PShape ps = parent.createShape();
		ps.beginShape(PApplet.TRIANGLE_STRIP);
		ps.texture(t);
		for (int i = 0; i < (numPointsH - 1); i++) { // For all the rings but top and bottom
			// Goes into the array here instead of loop to save time
			float coory = coorY[i];
			float cooryPlus = coorY[i + 1];

			float multxz = multXZ[i];
			float multxzPlus = multXZ[i + 1];

			for (int j = 0; j < numPointsW; j++) { // For all the pts in the ring
				ps.normal(-coorX[j] * multxz, -coory, -coorZ[j] * multxz);
				ps.vertex(coorX[j] * multxz * rx, coory * ry, coorZ[j] * multxz * rz, u, v);
				ps.normal(-coorX[j] * multxzPlus, -cooryPlus, -coorZ[j] * multxzPlus);
				ps.vertex(coorX[j] * multxzPlus * rx, cooryPlus * ry, coorZ[j] * multxzPlus * rz, u, v + changeV);
				u += changeU;
			}
			v += changeV;
			u = 0;
		}
		ps.endShape();
		return ps;
	}

	public Sphere collision(ArrayList<Sphere> spheres) {
		Sphere ballA = this;
		for (int j = 0; j < spheres.size(); j++) {
			Sphere ballB = (Sphere) spheres.get(j);
			if (ballA.getId() != ballB.getId() && ballA.pVector.dist(ballB.pVector) < (ballA.size + ballB.size) * 2) {
				// bounce(ballA, ballB);
				return ballB;
			}
		}
		return null;
	}
}
