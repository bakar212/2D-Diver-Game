
public class explosion {

  float x=250;
  float y =250;
  float radius =20;

  //explosion(){}

  explosion(int x, int y) {
    this.x=x;
    this.y=y;
  }

  void render() {//method which contains instructions to draw an explosion effect using processing in-built functions
    ellipse(x, y, radius, radius/2);//draw a round shape

    for (int degrees=0; degrees<360; degrees=degrees+10)//for loop to draw lines extending from the center 
    {
      line(x, y, x+radius*cos(degrees), y+radius*sin(degrees));//draw lines from center to the specified angle
      stroke(245, 126, 66);
    }
  }

  void animate() {//method in which lines increase in length (immitating an explosion effect) to be called in draw() event 
    while (radius<70)
    {
      radius=radius+20;
      render();
    }

    if (radius>=60) {
      radius=20;
    }
  }
}
