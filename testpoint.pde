import processing.serial.*; //<>//

Serial myport;
float [] val;
int x = 320, y = 180, z = 80;


void setup() {
  String portname = Serial.list()[0];
  myport  = new Serial(this, "/dev/ttyACM0", 9600);
  size(640, 360, P3D);
  noStroke();
}

void draw() {
  background(0, 0, 0);
  String buffer = myport.readStringUntil('\n');
  println(buffer);
  if (buffer != null)
    val = float(split(buffer, "$"));
  if (val != null && val.length == 3) {
    //println(val[0]+" $ "+val[1]+" $ "+val[2]);
    if (x < 640 && x >= 0)
      x = x+(int(val[0]*10));
    if (y < 360 && y >= 0)
      y = y+(int(val[1]*10));
    if (z > 30 && z < 200)
      z = z+(int(val[2]*5));


    noStroke();
    fill(255, 0, 0);
    lights();
    translate(x, y, z);
    sphere(50);
  }

  delay(100);
}
