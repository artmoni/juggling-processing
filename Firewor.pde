
class Firewor //Particle
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
  color[] rgbs = {color(200, 0, 50), color(0, 120, 200), color(0, 5, 230), color(0, 230, 160), color(80, 160, 90), color(250, 230, 50)};


  Firewor (float masse, 
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

  Firewor (float masse, 
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
    int Nfragments = (int) random(10, 20) - 10*niveau;

    float beta = atan2(vit.y, vit.x);
    float norme = sqrt(sq(vit.x)+sq(vit.y));

    for (int i = 0; i < Nfragments; i++)
    {
      PVector pos_fragment = pos;
      float alph = random(-PI, PI);
      PVector vit_fragment = new PVector(norme*cos(beta+alph)/10, norme*sin(beta+alph)/10);
      vit_fragment.mult(Nfragments/5);
      int masse_fragment = (int) (mass/2.0);
      Firewor b_inter = new Firewor (masse_fragment, pos_fragment, vit_fragment, niveau, rgb);
      firewors.add(b_inter);
    }
  }


  void display() {

    fill(00, 10);
    rect(0, 0, width, height);

    for (int i = 0; i < firewors.size(); i++)
    {
      Firewor f = (Firewor) firewors.get(i);
      f.actualise();
      f.dessine();
    }

    //Rajout durée de vie de la particule;
    for (int i = 0; i < firewors.size(); i++)
    {
      Firewor firewor = (Firewor) firewors.get(i);
      if (frameCount > firewor.date_mort)
      {
        if (firewor.niveau < 2) {
          firewor.explose();
        }
        firewors.remove(i);
      }
    }
    if (z < 0 || z > 3 )
    {
      float beta = random(PI/2.0 - PI/32.0, PI/2.0 + PI/32.0);
      color rgb1 = rgbs[(int)random(0, rgbs.length)];
      Firewor b_tiree = new Firewor(4000, new PVector(map(x, 0, width, lim[0].x, lim[1].x), map(y, height, 0, lim[0].y, lim[1].y)), new PVector(10*cos(beta), 10*sin(beta)), -1, rgb1); //ball_defaut;
      firewors.add(b_tiree);
    }
  }
}
