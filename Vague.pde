class Vague {

  //int co=255;
  float x;
  int j;
  color c;
  float mX;
  float mY;
  int couleur;

  Vague(float theta, int i, float m, float my) {
    x+= theta;
    j=i;
    mX=m;
    mY=my;
  }

  void display() {
    for (int i = j; i <= 100; i++) {

      float y = sin(x)*displayHeight/2;
      c = color(m-y, my-x, 255);
      //set(int(y),int(x),c);
      fill(c);

      //fill(127 + 127*sin(theta));
      ellipse(i*20, y + displayHeight/2, 10, 10);

      x += 0.2;
    }
  }
}
