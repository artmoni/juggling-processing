import processing.serial.*; //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>//

String SEPERATOR = "$";
int MIN_Z = 30, MAX_Z = 200;
int SPHERE_SIZE = 50;

Serial myport;

int defaultValueX, defaultValueY, defaultValueZ = 80;
int x, y, z = defaultValueZ;
int FrameRate = 60;
int angle = 0;
int id;

ArrayList firewors;
Firewor firewor_defaut;
Firewor fire;
PVector[] lim = new PVector[2];
int clics;

int inc = 20;
int i;
int zoom=25;

ArrayList<Sphere> spheres = new ArrayList();
boolean test = false;

float theta2 = 0.0;
float theta3 = 0.0;
float theta[]={0.0, 0.0, 0.0};
float m;
float my;
Vague [] v=new Vague[2];

int lastxtmp = defaultValueX;
int lastytmp = defaultValueY;

void setup() {
  myport  = new Serial(this, "/dev/ttyACM0", 9600);
  size(displayWidth, displayHeight, P3D);
  defaultValueX = displayWidth/2;
  defaultValueY = displayHeight/2;
  x = defaultValueX;
  y=defaultValueY; 
  noStroke();

  spheres.clear();

  lim[0] = new PVector(-750, -1); 
  lim[1] = new PVector(750, 1000);
  firewor_defaut = new Firewor(4000, new PVector(0, 0), new PVector(0, 10), -1, color(200, 0, 50));
  firewors = new ArrayList();
  firewors.add(new Firewor(4000, new PVector(0, 0), new PVector(0, 10), -1, color(200, 0, 50))); //ball_defaut);
  clics = 0;
  background(0);
}

void draw() {

  noStroke();
  String buffer = myport.readStringUntil('\n');
  println(buffer);
  if (buffer != null) {
    float val[] = (float(split(buffer, SEPERATOR)));
    readDataAndUpdate(val);

    for (Sphere mySphere : spheres) {
      checkcollision(mySphere);
      mySphere.display();
    }
  }
  delay(100);
}

void readDataAndUpdate(float[] val) {
  if (val != null) {
    Sphere currentSphere=null;

    int id = int(val[0]);
    for (Sphere maSphere : spheres) {
      if (maSphere.getId() == id) { 
        currentSphere=maSphere;
        break;
      }
    } //<>//
    if (!(currentSphere instanceof Sphere)) {
      Sphere sphere = new Sphere(SPHERE_SIZE, id, x, y, z);    
      spheres.add(sphere);
      currentSphere=sphere;
    }

    updateSphereValue(currentSphere, val);
  }
}



void checkcollision(Sphere sphere) {    
  if (sphere.checkCollision(x, y)) {
    //Pulse pulse = new Pulse(defaultValueX, defaultValueY);
    //pulse.display();
    smooth();
    theta[0] += 0.02;
    theta[1] += 0.03;
    theta[2] += 0.01;
    noStroke();
    fill(0);
    for (int j=0; j<2; j++) {
      m=100;
      my=60;
      v[i]=new Vague(theta[j], j, m, my);
      v[i].display();
      sphere.display();
    }
  } else {
    firewors.clear();
    sphere.display();
    //if (val[3] >= 3) {
    //  fire  = new Firewor(4000, new PVector(0, 0), new PVector(0, 100), -1, color(200, 0, 50));
    //  fire.display();
    //}
  }
}

void updateSphereValue(Sphere sphere, float []val) {
  if (val.length <=3)
    return;
  if ( x <= (displayWidth-SPHERE_SIZE*2) && x >SPHERE_SIZE*2 && y <(displayHeight-SPHERE_SIZE*2) && y>SPHERE_SIZE*2) {
    lastxtmp = x;
    lastytmp = y;
  }
  int  xtmp=x+(int((round(val[1]*10))));
  int ytmp = (y-(int(round(val[2]*15))));
  if (x!= xtmp && xtmp >= 25 && xtmp < displayWidth  )
    x = xtmp;
  if (y!=ytmp && ytmp >= 0 && ytmp < displayHeight) 
    y = ytmp;
  if (z > 30 && z < 200)
    z = (z+(int(round(val[3])*5)));
  if (z < 30)
    z = MIN_Z;
  if (z>200)
    z = MAX_Z;
  println(x+"-"+y+"-"+z);
  sphere.setX(x);
  sphere.setY(y);
  sphere.setZ(z);
}
