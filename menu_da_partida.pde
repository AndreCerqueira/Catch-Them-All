//menu de cima da partida

void menu() {
  fill(255);  
  rect(0, 0, width, 70);
  fill(0);

  stroke(0);
  strokeWeight(2);
  line(0, 70, width, 70);
  line(750, 70, 750, 0);

  

  
  pushMatrix();
    scale(1, -1);
    rotate(PI);
    textSize(50);
     fill(255);
      textSize(100);
     text("Score: "+score, 50, 100);
  popMatrix();


  fill(0);
  
}
