class Pulse { //<>// //<>//
  int x, y;
  int angle = 0;

  Pulse() {
    this.x = displayWidth/2;
    this.y = displayHeight/2;
  }

  Pulse(int x, int y) {
    this.x = x;
    this.y = y;
  }

  void display() {

    angle += 25;
    float val = cos(radians(angle)) * 50.0;
    for (int a = 0; a < 360; a += 75) {
      float xoff = cos(radians(a)) * val;
      float yoff = sin(radians(a)) * val;
      fill(0, 255, 255);
      ellipse(x + xoff, y + yoff, val, val);
    }
    fill(0, 255, 0);
    ellipse(x, y, 25, 25);
  }
}
