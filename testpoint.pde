import processing.serial.*; //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>//

String SEPERATOR = "$";
int MIN_Z = 30, MAX_Z = 200;
int SPHERE_SIZE = 50;

Serial myport;
float [] val;
int defaultValueX, defaultValueY, defaultValueZ = 80;
int x, y, z = defaultValueZ;
int FrameRate = 60;
int angle = 0;
IntList clubs = new IntList();
int id;

ArrayList firewors;
Firewor firewor_defaut;
Firewor fire;
PVector[] lim = new PVector[2];
int clics;

int inc = 20;
int i;
int zoom=25;

Sphere sphere;

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
  sphere = new Sphere(SPHERE_SIZE);


  lim[0] = new PVector(-750, -1); 
  lim[1] = new PVector(750, 1000);
  firewor_defaut = new Firewor(4000, new PVector(0, 0), new PVector(0, 10), -1, color(200, 0, 50));
  firewors = new ArrayList();
  firewors.add(new Firewor(4000, new PVector(0, 0), new PVector(0, 10), -1, color(200, 0, 50))); //ball_defaut);
  clics = 0;
}

void draw() {
  background(0);
  String buffer = myport.readStringUntil('\n');
  println(buffer);
  if (buffer != null)
    val = (float(split(buffer, SEPERATOR)));
  if (val != null && val.length == 4) {
    addId();    //println(val[0]+" $ "+val[1]+" $ "+val[2]);
    println("X  =  "+round(val[1]));
    println("h = "+displayHeight+" Y  =  "+val[2]+"  -- "+int(round(val[2]*10))+ " -- "+y+"  --  "+ (y-(round(val[2]*10))));
    //println("Z  =  "+round(val[3]));
    
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
    if (sphere.checkCollision(x, y)) {
      Pulse pulse = new Pulse(defaultValueX, defaultValueY);
      pulse.display();
      //smooth();
      //theta[0] += 0.02;
      //theta[1] += 0.03;
      //theta[2] += 0.01;
      //noStroke();
      //fill(0);
      //for (int i=0; i<2; i++) {
      //  m=100;
      //  my=60;
      //  v[i]=new Vague(theta[i], i, m, my);
      //  v[i].display();
      //}
      //fire  = new Firewor(4000, new PVector(0, 0), new PVector(0, 100), -1, color(200, 0, 50));
      //  fire.display();
      sphere.display(lastxtmp, lastytmp, z);
    } else {
      firewors.clear();
      sphere.display(x, y, z);
      if (val[3] >= 3) {
        
      }
    }
  }
  delay(100);
}

void addId() {
  id = 0;
  if (!clubs.hasValue(id))
    clubs.appendUnique(id);
}
