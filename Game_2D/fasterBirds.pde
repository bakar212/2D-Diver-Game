public class fasterBirds extends bird {

  fasterBirds(int x, int y) {
    super(x, y);
  }

  @Override
    void display() {
    bird1.resize(px, py);
    bird2.resize(px, py);
    bird3.resize(px, py);
    bird4.resize(px, py);

    if (counter<7) {
      image(bird1, x, y);
    } else if (counter<14) {
      image(bird2, x, y);
    } else if (counter<21) {
      image(bird3, x, y);
    } else if (counter<28) {
      image(bird4, x, y);
    } else if (counter<35) {
      image(bird2, x, y);
    } else if (counter>35) {
      counter=0;
    }
    counter++;//increments the counter by 1 for each cycle
  }


  @Override
    void moveBird() {
    x=x+random(-2, -6);//randomly shifts the x value of the image between -1 and -3 pixels
    y=y+random(-14, 5);//changes the y value of the image by value between -8.5 and 2.5

    if (y<0 || y>height || x<0 || x>width) {//if the image goes off the screen edges
      x=(int)random(width-50);//then return the image's x co-ordinate to this value
      y=(int)random(height-50, height/2);//return the y co-ordinate by this random value
    }
  }
}
