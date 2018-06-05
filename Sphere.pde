class Sphere //<>// //<>// //<>//
{
  int size;
  boolean buffer = false;
  int posX, posY, posZ;
  int id;

  Sphere() {
    size = 30;
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
