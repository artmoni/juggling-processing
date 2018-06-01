class Sphere //<>// //<>// //<>//
{
  int size;

  Sphere() {
    size = 30;
  }
  Sphere(int d) {
    size=d;
  }
  void display(int x, int y, int z) {

    noStroke();

    fill(255, 0, 0);
    pointLight(255, 0, 0, width/2, height/2, 400);
    translate(x, y, z);
    
    sphere(size);
    fill(0, 255, 255);
    translate(x, y, z);
    if (checkCollision(x, y)) {  
        Pulse pulse = new Pulse(50,50);
        pulse.display();
    }
  }

  boolean checkCollision(int x, int y) {
    println("test collision -- x = "+x+"  y = "+y+" --- W = "+displayWidth+"  H = "+displayHeight);
    if (x > (displayWidth-size*2) || x < size*2) {
      println("COLISION X");
      return true;
    }
    if (y> (displayHeight-size*2) || y < size*2) {
      println("COLISION Y");
      return true;
    } else
      return false;
  }



}
