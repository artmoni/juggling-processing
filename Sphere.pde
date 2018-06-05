class Sphere //<>//
{
  int size;
  int posX, posY, posZ;
  int id;

  Sphere() {
    size = 30;
    posX = displayWidth/2;
    posY = displayHeight/2;
    posZ = 80;
  }
  Sphere(int d, int id, int x, int y, int z) {
    this.size=d;
    this.id = id;
    this.posX = x;
    this.posY = y;
    this.posZ = z;
  }
  
  void display( ) {

    noStroke();

    pushMatrix();
    fill(255, 0, 0);
    lights();

    translate(this.posX, this.posY, this.posZ);
    sphere(size);
    popMatrix();
  }

  void displayCoord() {
    textSize(20);
    fill(255);
    text("x = "+this.getPosX(), posX-5*size, posY);
    text("y = "+this.getPosY(), posX-5*size, posY+20);
    text("z = "+this.getPosZ(), posX-5*size, posY+40);
    text("ID = "+this.getId(), posX-5*size, posY+60);
  }
  
  boolean checkCollision( int x, int y) {
    //  println("test collision -- x = "+x+"  y = "+y+" --- W = "+displayWidth+"  H = "+displayHeight);

    if (x > (displayWidth-size*2) || x < size*2) {
      println("COLISION X");
      return true;
    }
    if (y> (displayHeight-size*2) || y < size*2) {
      println("COLISION Y");
      return true;
    } else
      return false;
  } //<>//

  int getId() {
    return this.id;
  }
  int getPosX() {
    return this.posX;
  }
  int getPosY() {
    return this.posY;
  }
  int getPosZ() {
    return this.posZ;
  }
   int getSize() {
    return this.size;
  }
  
  void setX(int x) {
    this.posX = x;
  }
  void setY(int y) {
    this.posY = y;
  }
  void setZ(int z) {
    this.posZ = z;
  }
  void destroy() {
    this.size = 0;
  }
}
