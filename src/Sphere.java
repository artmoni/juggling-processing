import processing.core.PApplet;
import processing.core.PVector;

class Sphere //<>// //<>// //<>//
{
  int size;
  int id;
//  int posX;
//  int posY;
//  int posZ;
  PVector pVector;
  PApplet parent;

  Sphere() {
    size = 30;
  }
  Sphere(int d,int id ,PVector pvector, PApplet parent) {
    size=d;
    this.id = id;
    this.pVector = pvector;
    this.parent = parent;
  }
  void display() {

    parent.noStroke();

    parent.fill(255, 0, 0);
    parent.lights();

    parent.translate(this.pVector.x, this.pVector.y, this.pVector.z);
    parent.sphere(size);
    
 
  }
  void displayCoord() {
	    parent.textSize(20);
	    parent.fill(255);
	    parent.text("x = "+this.getPosX(), this.pVector.x-5*size, this.pVector.y);
	    parent.text("y = "+this.getPosY(), this.pVector.x-5*size, this.pVector.y+20);
	    parent.text("z = "+this.getPosZ(), this.pVector.x-5*size, this.pVector.y+40);
	    parent.text("ID = "+this.getId(), this.pVector.x-5*size, this.pVector.y+60);
	  }
	  

  boolean checkCollision(int x, int y) {
  //  println("test collision -- x = "+x+"  y = "+y+" --- W = "+displayWidth+"  H = "+displayHeight);
    if (x > (parent.displayWidth-size*2) || x < size*2) {
    	parent.println("COLISION X");
      return true;
    }
    if (y> (parent.displayHeight-size*2) || y < size*2) {
    	parent.println("COLISION Y");
      return true;
    } else
      return false;
  }
  int getId() {
	    return this.id;
	  }
	  float getPosX() {
	    return this.pVector.x;
	  }
	  float getPosY() {
	    return this.pVector.y;
	  }
	  float getPosZ() {
	    return this.pVector.z;
	  }
	   float getSize() {
	    return this.size;
	  }
	  
	  void setX(int x) {
	    this.pVector.x = x;
	  }
	  void setY(int y) {
	    this.pVector.y = y;
	  }
	  void setZ(int z) {
	    this.pVector.z = z;
	  }
	  void destroy() {
	    this.size = 0;
	  }
}
