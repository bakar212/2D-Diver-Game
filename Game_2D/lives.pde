public class lives {

  int lives;
  int rectX =0;//x-co-ordinate for rectangle 
  int i;//loop counter

  lives(int livesCount) {
    this.lives = livesCount;//constructor with lives variable
  }

  void drawLives() {
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
