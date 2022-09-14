public class diver {
  PImage diver1 = loadImage("diverleft.png");
  PImage diver2 = loadImage("diverRight.png");
  final int diverX = 300;//original image pixel dimensions
  final int diverY = 290;

  int x = width/2;//image pixel positioning
  int y =30;

  diver(int x, int y) {//constructor
    this.x =x;
    this.y=y;
  }

  void render() {
    diver1.resize(diverX/4, diverY/4);
    diver2.resize(diverX/4, diverY/4);

    if (keyCode == LEFT) {//if left key is pressed
      image(diver1, x, y);//display left facing diver
      x=x-2;//move the image's pixel co=ordinate by a value of -2
    } else if (keyCode == RIGHT)//if right key is pressed
    {
      image(diver2, x, y);//display right facing diver
      x=x+2;//move the image's pixel co=ordinate by a value of +2
    } else {
      image(diver2, x, y);
    }

    if (keyCode == UP) {
      y=y-2;
    }//if up key is pressed increment y by -2
    if (keyCode == DOWN) {
      y=y+2;
    } //if down key is pressed increment y by -2
  }

  void blockEdgeMovement() {//blocks the diver object from floating off the edge of the screen
    if (x<=0) {
      x=0;
    }
    if (x>=width-75) {
      x=width-75;
    }
    if (y<=0) {
      y=0;
    }
    if (y>=height-73) {
      y=height-73;
    }
  }

  void updateDiver() {
    render();
    blockEdgeMovement();
  }
}
