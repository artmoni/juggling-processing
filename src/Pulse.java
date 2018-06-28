import processing.core.PApplet;

class Pulse {
	int x, y;
	PApplet parent;
	int angle = 0;

	Pulse(PApplet parent) {
		this.parent = parent;
		this.x = parent.displayWidth / 2;
		this.y = parent.displayHeight / 2;
	}

	Pulse(int x, int y, PApplet parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}

	void display() {

		angle += 5;
		float val = (float) (PApplet.cos(PApplet.radians((float) angle)) * 12.0);
		for (int a = 0; a < 360; a += 70) {
			float xoff = PApplet.cos(PApplet.radians(a)) * val;
			float yoff = PApplet.sin(PApplet.radians(a)) * val;
			parent.fill(0, 255, 255);
			parent.ellipse(x + xoff, y + yoff, val, val);
		}
		parent.fill(0, 255, 0);
		parent.ellipse(x, y, 10, 10);
	}
}
