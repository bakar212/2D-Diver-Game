
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

void setup() {
  size(800, 900);
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

void moveBackgroundImage() {//function to define movement of background image to be called within draw() 
  image(Background, 0, y);
  image(Background, 0, y+Background.height);
  y = y-4;
  if (y<=-Background.height) {
    y=0;
  }
}


void draw() {

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

void keyPressed() {

  if (keyCode == 32 && gameMode==game.splashScreen) {//when the player presses the spacebar at splashscreen
    gameMode=game.gameRunning;//gameMode is now gameRunning
    setup();//call to setup method to reset/perform the setup instructions for when the gameMode is gameRunning
  }

  if (key == 'r' && gameMode == game.gameFinished) {//when player presses 'r' key at the gameover screen
    gameMode=game.gameRunning;//gameMode is now gameRunning
    setup();//call to setup method to reset/perform the setup instructions for when the gameMode is gameRunning
  }
}
