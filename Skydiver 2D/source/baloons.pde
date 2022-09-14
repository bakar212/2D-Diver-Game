public class baloons {

  int x, y;//x and y co-ordinate origins for drawing a baloon
  int baloonDY = -5;//rate of change of y co-ordinate pixels for baloon object

  baloons() {//basic constructor
    x = (int)random(width);
    y = height+30;
  }

  baloons(int x, int y) {//overloaded constructor prompting for values of x and y
    this.x=x;
    this.y =y;
  }

  void renderBaloon() {//draws a red colured baloon
    fill(255, 0, 0);
    ellipse(x, y, 40, 60);
    line(x, y+30, x, y+90);
    fill(0, 0, 0);
  }

  void moveBaloon() {
    if (detectBaloonCollision()==true) {//if a collision is detected for the baloon with the player 
      score=score+10;//increase the score by 10
    }
    if (y<=-90 || detectBaloonCollision() == true) {//if y is out of screen range or boolean function returns true
      //reset it towards the bottom
      y=height+30;
      x=(int)random(width);
    }
    //increment the score counter by 10
    else {
      y=y+baloonDY;//otherwise increment the position of y by 5
    }
  }


  boolean detectBaloonCollision() {//function to return true when baloon collides with diver 

    return (abs(this.x-(d1.x+38))<=58 && abs(this.y-(d1.y+36))<=66);//sets the mathematical boundaries between the individual co-ordiantes of baloon 
    //object and diver object to test for a collision
  }
}
