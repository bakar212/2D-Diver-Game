public class bird {
  PImage bird1 = loadImage("bird1.PNG");
  PImage bird2 = loadImage("bird2.PNG");
  PImage bird3 = loadImage("bird3.PNG");
  PImage bird4 = loadImage("bird4.PNG");
  float x=0;//pixel positions of image
  float y=0;
  int counter =0;//counter for display method
  int px =60;//rescaling of image by x pixels
  int py = 40;//rescaling of image by y pixels

  bird(int x, int y) {
    this.x = x;
    this.y =y;
  }

  void display() {//method which displays several images
    bird1.resize(px, py);
    bird2.resize(px, py);
    bird3.resize(px, py);
    bird4.resize(px, py);

    if (counter<15)/*Display image sequence with respect to counter */ {
      image(bird1, x, y);//display image 1
    } else if (counter<30) {
      image(bird2, x, y);//display image 2
    } else if (counter<45) {
      image(bird3, x, y);//display image 3
    } else if (counter<60) {
      image(bird4, x, y);//display image 4
    } else if (counter<75) {
      image(bird2, x, y);//display image 2(once again)
    } else if (counter>75) {
      counter=0;//reset counter when the value is greater than 0
    }
    counter++;//increments the counter by 1 for each cycle
  }

  void moveBird() {
    x=x+random(-1, -3);//randomly shifts the x value of the image between -1 and -3 pixels
    y=y+random(-8.5, 2.5);//changes the y value of the image by value between -8.5 and 2.5

    if (y<0 || y>height || x<0 || x>width) {//if the image goes off the screen edges
      x=(int)random(width-50);//then return the image's x co-ordinate to this value
      y=(int)random(height-50, height/2);//return the y co-ordinate by this random value
    }
  }

  boolean detectCollision() {//function to return true when bird collides with diver
    if (abs((this.x+30)-(d1.x+38))<=68 && abs((this.y+20)-(d1.y+36))<=56)//sets mathematical boundaries between individual x,y co-ordinates
    {    //for bird and diver objects
      return true;
    } else
    {
      return false;
    }
  }

  void updateBird() {//collect the previous two methods and call them in one
    display();
    moveBird();
  }
}
