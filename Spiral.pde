
void display(float x,float y){
  spiral(0,300);
}

void spiral(float x, float y) {
  //translate(width/2,height/2);
  float d= map(i, 0, 360*15, 0, 500);
  float a=radians(i);
  //fill(242,105,233);
  color c = color(255-d, 255, 255);
  fill(c);
  stroke(206, 6, 193);
  ellipse(x + cos(a)*d, y + sin(a)*d, 2+d/inc, 2+d/inc);

  //zoom+=10;
  i += inc;
}
