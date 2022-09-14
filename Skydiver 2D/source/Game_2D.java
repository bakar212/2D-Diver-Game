import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Game_2D extends PApplet {


enum game {
  splashScreen, gameRunning, gameFinished
}//enumaratedset of values used to identify differnet game modes

game gameMode = game.splashScreen;//currently the game is on the splash screen

int lives;
int score;
int bestScore;//record of highest score 

PImage Background;
int y = 0;//y location for backgroundimage
diver d1;//an instance of a diver object
explosion e;//instance of an explosion object
bird[] birds;//reference to an Array of multiple bird objects
fasterBirds[] fasterBirds;//referance to an Array of multiple fasterBirds objects which is a sub class of the bird class
lives l;//instance of the lives object
baloons[] baloons;//refernce to an Array of multiple baloon objects

//public void settings(){size(800,900);}

public void setup() {
  
  //switch case scenario introduced for the different game modes as the setup instructions as unique
  switch(gameMode) {
  case gameRunning:
    //size(800, 900);
    //settings();
    Background = loadImage("sky.gif");
    Background.resize(width, height);
    image(Background, 0, 0);//display the loaded image at specified co-ordinates
    e = new explosion(250, 250);//the explosion object e has been assigned its class member properties through calling its constructor
    d1 = new diver(500, 10);//the diver object d1 has been assigned its class member properties

    birds = new bird[6];//bird object given an array instruction of length 6 i.e 6 bird objects 
    fasterBirds=new fasterBirds[2];//fasterBirds object given array instruction of length 2 i.e 2 fasterbird objects
    baloons = new baloons[3];//baloons object given array instruction of length 3 i.e 3 baloons objects
    lives=6;
    score=0;
    //String[] initialiseBestScore = {"0"};//bestScore needs an initial value to read off a text file
    //saveStrings("variables.txt",initialiseBestScore);//save this string variable in a text file
    String[] Var = loadStrings("variables.txt");//load off all strings from the text file to a new string variable
    bestScore = Integer.valueOf(Var[0]);//convert the string variable at index[0] to an intger and store it in the int variable

    for (int i=0; i<baloons.length; i++) {//for loop to iterate through every index of baloons array object
      baloons[i] = new baloons((int)random(width), (int)random(height/2));//assign each new baloons object of [i] index within the array its class member values
    }

    for (int i =0; i<birds.length; i++)/*for the length of the array of birds keep looping*/ {//for loop to iterate through every index of birds array object
      birds[i] = new bird((int)random(width), (int)random(height));//create a new bird object with each loop with random integers as placeholder values
    }


    for (int i=0; i<fasterBirds.length; i++) {//loop through each index of the fasterbirds array object
      fasterBirds[i]= new fasterBirds((int)random(width), (int)random(height));//assign each new bird object its placholder values
    }

    break;

  case splashScreen://loading screen scenario at the start
    //settings();
    Background = loadImage("sky.gif");
    Background.resize(width, height);
    image(Background, 0, 0);//display the specific image as background

    break;

  case gameFinished://game finished scenario i.e all lives are lost
    //settings();
    Background = loadImage("sky.gif");
    Background.resize(width, height);
    image(Background, 0, 0);//display specific image as background

    break;
  }
}//end of setup() function

public void moveBackgroundImage() {//function to define movement of background image to be called within draw() 
  image(Background, 0, y);
  image(Background, 0, y+Background.height);
  y = y-4;
  if (y<=-Background.height) {
    y=0;
  }
}


public void draw() {

  l=new lives(lives);//object created in draw() rather than setup() as it prone to change constantly with each draw() cycle
  if (lives==0 && gameMode==game.gameRunning)/*if the game is still running and there are no lives left end the game*/ {
    gameMode=game.gameFinished;
  }

  if (gameMode==game.splashScreen) {//draw method executes only if the gameMode is set to splashScreen

    moveBackgroundImage();

    fill(240, 189, 134);
    ellipse(width/2, height/2, 700, 400);

    fill(110, 0, 24);
    textSize(100);
    text("SKYDIVER 2D", 80, height/2+20);
    textSize(25);
    text("Press SPACE to start the game", 200, height/2+80);

    textSize(15);
    text("DEVELOPED BY ABUBAKAR", width-200, height-20);
  }

  if (gameMode==game.gameRunning) {//draw method executes only if the gameMode is set to gameRunning;
    moveBackgroundImage();
    l.drawLives();//run the specific procedure lives object
    stroke(0);
    textSize(23);
    fill(0);
    text("SCORE: "+score, 600, 38);
    text("BEST SCORE: "+bestScore, 600, 68);
    d1.updateDiver();//call to the specific procedure for the diver object which controls the divers location on the screen

    if (score>bestScore)//if current score has a greter value than current bestScore
    {
      String[] newBestScore = {Integer.toString(score)};//convert score value to integer and store in a new string variable
      saveStrings("variables.txt", newBestScore);//write this new value into the text file
    }

    for (int i=0; i<baloons.length; i++) {//loop created to reference each baloons object through the use of index [i] with same programming instructions
      baloons[i].renderBaloon();//call to the specific baloons object procedure which displays a baloon on the screen
      baloons[i].moveBaloon();//call to baloon objects procedure method which sets instructions on its movement across the screen
      if (baloons[i].detectBaloonCollision()) {
        e.x=baloons[i].x+20;//set the variable x for the explosion object e
        e.y=baloons[i].y+30;//set the variable y for the explosion object e
        e.animate();//call method of object which animates an explosion effect
      }
    }

    for (int i=0; i<birds.length; i++) {//loop created to reference each bird object with the same programming instructions
      if (birds[i]!=null)/*if the bird object exists*/ { 
        birds[i].updateBird();//run the current bird objects procedure to display and move it
        if (birds[i].detectCollision()==true)/*if a collision is detected for the current object*/ {
          e.x = birds[i].x+30;//set the variable x for the explosion object e
          e.y = birds[i].y+20;//set the variable y for the explosion object e
          e.animate();//call method of object which animates an explosion effect
          birds[i]=null;//the current object is now null
          lives=lives-1;//decrease the lives by 1
          println("Current lives: "+lives);
        }
      }
    }


    for (int i=0; i<fasterBirds.length; i++) {//loop created to reference each bird object with the same programming instructions
      if (fasterBirds[i]!=null)/*if the bird object exists*/ { 
        fasterBirds[i].updateBird();//run the current bird objects procedure to display and move it
        if (fasterBirds[i].detectCollision()==true)/*if a collision is detected for the current object*/ {
          e.x = fasterBirds[i].x+30;//set the variable x for the explosion object e
          e.y = fasterBirds[i].y+20;//set the variable y for the explosion object e
          e.animate();//call method of object which animates an explosion effect
          fasterBirds[i]=null;//the current object is now null
          lives=lives-1;//decrease the lives by 1
          println("Current lives: "+lives);
        }
      }
    }
  }

  if (gameMode == game.gameFinished) {//draw method executes only if the gameMode is set to gameFinished;
    moveBackgroundImage();

    textSize(100);
    fill(255, 0, 0);
    text("GAME OVER!", 100, height/2-80);
    fill(0);
    textSize(35);
    text("Your final score: "+score, 220, height/2+20);
    text("Game best score: "+bestScore, 220, height/2+70);
    textSize(25);
    text("Press R to restart the game", 210, height/2+200);
  }
}

public void keyPressed() {

  if (keyCode == 32 && gameMode==game.splashScreen) {//when the player presses the spacebar at splashscreen
    gameMode=game.gameRunning;//gameMode is now gameRunning
    setup();//call to setup method to reset/perform the setup instructions for when the gameMode is gameRunning
  }

  if (key == 'r' && gameMode == game.gameFinished) {//when player presses 'r' key at the gameover screen
    gameMode=game.gameRunning;//gameMode is now gameRunning
    setup();//call to setup method to reset/perform the setup instructions for when the gameMode is gameRunning
  }
}
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

  public void renderBaloon() {//draws a red colured baloon
    fill(255, 0, 0);
    ellipse(x, y, 40, 60);
    line(x, y+30, x, y+90);
    fill(0, 0, 0);
  }

  public void moveBaloon() {
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


  public boolean detectBaloonCollision() {//function to return true when baloon collides with diver 

    return (abs(this.x-(d1.x+38))<=58 && abs(this.y-(d1.y+36))<=66);//sets the mathematical boundaries between the individual co-ordiantes of baloon 
    //object and diver object to test for a collision
  }
}
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

  public void display() {//method which displays several images
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

  public void moveBird() {
    x=x+random(-1, -3);//randomly shifts the x value of the image between -1 and -3 pixels
    y=y+random(-8.5f, 2.5f);//changes the y value of the image by value between -8.5 and 2.5

    if (y<0 || y>height || x<0 || x>width) {//if the image goes off the screen edges
      x=(int)random(width-50);//then return the image's x co-ordinate to this value
      y=(int)random(height-50, height/2);//return the y co-ordinate by this random value
    }
  }

  public boolean detectCollision() {//function to return true when bird collides with diver
    if (abs((this.x+30)-(d1.x+38))<=68 && abs((this.y+20)-(d1.y+36))<=56)//sets mathematical boundaries between individual x,y co-ordinates
    {    //for bird and diver objects
      return true;
    } else
    {
      return false;
    }
  }

  public void updateBird() {//collect the previous two methods and call them in one
    display();
    moveBird();
  }
}
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

  public void render() {
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

  public void blockEdgeMovement() {//blocks the diver object from floating off the edge of the screen
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

  public void updateDiver() {
    render();
    blockEdgeMovement();
  }
}

public class explosion {

  float x=250;
  float y =250;
  float radius =20;

  //explosion(){}

  explosion(int x, int y) {
    this.x=x;
    this.y=y;
  }

  public void render() {//method which contains instructions to draw an explosion effect using processing in-built functions
    ellipse(x, y, radius, radius/2);//draw a round shape

    for (int degrees=0; degrees<360; degrees=degrees+10)//for loop to draw lines extending from the center 
    {
      line(x, y, x+radius*cos(degrees), y+radius*sin(degrees));//draw lines from center to the specified angle
      stroke(245, 126, 66);
    }
  }

  public void animate() {//method in which lines increase in length (immitating an explosion effect) to be called in draw() event 
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
public class fasterBirds extends bird {

  fasterBirds(int x, int y) {
    super(x, y);
  }

  public @Override
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


  public @Override
    void moveBird() {
    x=x+random(-2, -6);//randomly shifts the x value of the image between -1 and -3 pixels
    y=y+random(-14, 5);//changes the y value of the image by value between -8.5 and 2.5

    if (y<0 || y>height || x<0 || x>width) {//if the image goes off the screen edges
      x=(int)random(width-50);//then return the image's x co-ordinate to this value
      y=(int)random(height-50, height/2);//return the y co-ordinate by this random value
    }
  }
}
public class lives {

  int lives;
  int rectX =0;//x-co-ordinate for rectangle 
  int i;//loop counter

  lives(int livesCount) {
    this.lives = livesCount;//constructor with lives variable
  }

  public void drawLives() {
    for (i=0; i<=lives-1; i++)/*for the number of lives*/ {  
      fill(0, 256, 0);
      rect(80+rectX, 20, 10, 20);//draw rectangle
      textSize(23);
      text("LIVES: ", 10, 38);
      rectX=rectX+20;//space out positions of x x-co-ordinate for each new rectangle by 10
      if (rectX>=120) {
        rectX=0;
      }//ensures it resets to 0 to ensure rectangles don't appear to be moving when this method is called in draw
    }
  }
}
  public void settings() {  size(800, 900); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Game_2D" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
