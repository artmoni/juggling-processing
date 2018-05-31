import processing.serial.*; //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>// //<>//

String SEPERATOR = "$";
int MIN_Z = 30, MAX_Z = 200;
int SPHERE_SIZE = 50;

Serial myport;
float [] val;
int defaultValueX, defaultValueY, defaultValueZ = 80;
int x, y, z = defaultValueZ;
int FrameRate = 60;
int angle = 0;
IntList clubs;
int id;

ArrayList balls;
Ball ball_defaut;
PVector[] lim = new PVector[2];
color[] rgb = {color(200, 0, 50), color(0, 120, 200), color(0, 5, 230), color(0, 230, 160), color(80, 160, 90), color(250, 230, 50)};
int clics;

void setup() {
  String portname = Serial.list()[0];
  myport  = new Serial(this, "/dev/ttyACM1", 9600);
  size(displayWidth, displayHeight, P3D);
  defaultValueX = displayWidth/2;
  defaultValueY = displayHeight/2;
  x = defaultValueX;
  y=defaultValueY; 
  noStroke();

  lim[0] = new PVector(-750, -1); 
  lim[1] = new PVector(750, 1000);
  ball_defaut = new Ball(4000, new PVector(0, 0), new PVector(0, 10), -1, color(200, 0, 50));
  balls = new ArrayList();
  balls.add(new Ball(4000, new PVector(0, 0), new PVector(0, 10), -1, color(200, 0, 50))); //ball_defaut);
  clics = 0;
}

void draw() {
  background(0);
  String buffer = myport.readStringUntil('\n');
  println(buffer);
  if (buffer != null)
    val = (float(split(buffer, SEPERATOR)));
  if (val != null && val.length == 3) {
    //addId();
    displaySphere();
  }

  delay(1000);
}

void addId() {
  id = 0;
  if (!clubs.hasValue(id))
    clubs.appendUnique(id);
}
void displaySphere() {
  //println(val[0]+" $ "+val[1]+" $ "+val[2]);
  println("X  =  "+round(val[1]));
  println("h = "+displayHeight+" Y  =  "+val[2]+"  -- "+int(round(val[2]*10))+ " -- "+y+"  --  "+ (y-(round(val[2]*10))));
  println("Z  =  "+round(val[3]));

  int  xtmp=x+(int((round(val[0]*10))));
  int ytmp = (y-(int(round(val[1]*15))));
  if (x!= xtmp && xtmp >= 25 && xtmp < displayWidth && (xtmp > width-25 || xtmp < 25 ) )
    x = xtmp;
  if (y!=ytmp && ytmp >= 0 && ytmp < displayHeight && (ytmp > height-25 || ytmp < 25)) 
    y = ytmp;
  if (z > 30 && z < 200)
    z = MIN_Z;//(z+(int(round(val[3])*5)));
  if (z < 30)
    z = MIN_Z;
  if (z>200)
    z = MAX_Z;
       println(x+"-"+y+"-"+z);
    noStroke();
    fill(255, 0, 0);
    lights();
    translate(x, y, z);
    sphere(SPHERE_SIZE);
}

void displayArt() {

  fill(00, 10);
  rect(0, 0, width, height);

  for (int i = 0; i < balls.size(); i++)
  {
    Ball ball = (Ball) balls.get(i);
    ball.actualise();
    ball.dessine();
  }

  //Rajout durée de vie de la particule;
  for (int i = 0; i < balls.size(); i++)
  {
    Ball ball = (Ball) balls.get(i);
    if (frameCount > ball.date_mort)
    {
      if (ball.niveau < 2) {
        ball.explose();
      }
      balls.remove(i);
    }
  }
  if (round(val[2]) < 0)
  {
    float beta = random(PI/2.0 - PI/32.0, PI/2.0 + PI/32.0);
    color rgb1 = rgb[(int)random(0, rgb.length)];
    Ball b_tiree = new Ball(4000, new PVector(map(x, 0, width, lim[0].x, lim[1].x), map(y, height, 0, lim[0].y, lim[1].y)), new PVector(10*cos(beta), 10*sin(beta)), -1, rgb1); //ball_defaut;
    balls.add(b_tiree);
  }
}

class Ball //Particle
{
  PVector pos; //(~10m)
  PVector vit; //(~10m/s = 36km/h)
  PVector acc; //(~10g)

  float mass; //(~100g);
  float ray;
  float ray_ecran;
  float dt;
  color rgb;

  int date_naissance;
  int date_mort;
  int niveau;
  boolean explosee;

  Ball (float masse, 
    PVector pos_i, 
    PVector vit_i, 
    PVector acc_i, 
    int niveau_parent, 
    color couleur)
  {
    mass = masse;
    pos = pos_i;
    vit = vit_i;
    acc = acc_i;

    ray = mass/1000.0;
    ray_ecran = abs(map(ray, 0, lim[1].x, 0, width/2.0));
    dt = 1;
    rgb = color(255, 0, 100);

    niveau = niveau_parent+1;
    date_naissance = frameCount;
    date_mort = (int)(date_naissance + FrameRate*(1*niveau+1) - FrameRate/4.0 + random(-FrameRate/2, FrameRate/2));
    explosee = false;
    rgb = couleur;
  }

  Ball (float masse, 
    PVector pos_i, 
    PVector vit_i, 
    int niveau_parent, 
    color couleur)
  {
    this (masse, pos_i, vit_i, new PVector(0, -0.02), niveau_parent, couleur);
  }

  void dessine()
  {
    PVector pos_ecran = new PVector(map(pos.x, lim[0].x, lim[1].x, 0, width), map(pos.y, lim[0].y, lim[1].y, height, 0));
    if (ray_ecran == 0) {
      ray_ecran = 2;
    }
    ellipseMode(CENTER);
    noStroke(); 
    fill(rgb);
    ellipse(pos_ecran.x, pos_ecran.y, 2*ray_ecran, 2*ray_ecran);
  }

  void actualise()
  {
    //Aléa
    float beta = atan2(vit.y, vit.x);
    float alph = random(-PI/512.0, PI/512.0);
    float norme = sqrt(sq(vit.x)+sq(vit.y));
    vit = new PVector(norme*cos(beta+alph), norme*sin(beta+alph));

    //F° vitales
    vit = PVector.add(vit, PVector.mult(acc, dt));
    pos = PVector.add(pos, PVector.mult(vit, dt));
  }

  void explose()
  {
    explosee = true;
    int Nfragments = (int) random(20, 30) - 5*niveau;

    float beta = atan2(vit.y, vit.x);
    float norme = sqrt(sq(vit.x)+sq(vit.y));

    for (int i = 0; i < Nfragments; i++)
    {
      PVector pos_fragment = pos;
      float alph = random(-PI, PI);
      PVector vit_fragment = new PVector(norme*cos(beta+alph)/10, norme*sin(beta+alph)/10);
      vit_fragment.mult(Nfragments/5);
      int masse_fragment = (int) (mass/2.0);
      Ball b_inter = new Ball (masse_fragment, pos_fragment, vit_fragment, niveau, rgb);
      balls.add(b_inter);
    }
  }
}

void drawPulses() {

  angle += 5;
  float val = cos(radians(angle)) * 12.0;
  for (int a = 0; a < 360; a += 75) {
    float xoff = cos(radians(a)) * val;
    float yoff = sin(radians(a)) * val;
    fill(0);
    ellipse(x + xoff, y + yoff, val, val);
  }
  fill(255);
  ellipse(x, y, 2, 2);
}
