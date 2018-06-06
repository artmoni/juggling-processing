class Spiral {
  float x;
  float y ;

  Spiral() {
    this.x = displayWidth/2;
    this.y = displayHeight/2;
  }

  Spiral(float x, float y) {
    this.x = x;
    this.y = y;
  }
  void display() {
    float d= map(i, 0, 360*15, 0, 500);
    float a=radians(i);
    //fill(242,105,233);
    color c = color(255-d, 255, 255);
    fill(c);
    stroke(206, 6, 193);
    ellipse(x + cos(a)*d, y + sin(a)*d, 20, 20);

    //zoom+=10;
    i += inc;
  }
}
