//botao de inicio

void botao() {
  //rectMode(CENTER);

  pushMatrix();
    scale(1, -1);
    rotate(PI);
    stroke(200);
    strokeWeight(10);
    
    fill(255);
    rect(790, 75, 450, 150);
    
    textSize(70);
    fill(0);
    text("Começar", 860, 175);
  popMatrix();
  
  pushMatrix();
    scale(1, -1);
    rotate(PI);
    stroke(200);
    strokeWeight(10);
    
    fill(255);
    rect(770, 775, 490, 150);
    
    textSize(60);
    fill(0);
    text("Recorde: "+maior_score, 810, 875);
  popMatrix();

}

void checkButton() {
  if (avgX > 790-50 && avgX < 790+363 && avgY > 75-50 && avgY < 75+218) {
    overButton = true;
  } else {
    overButton = false;
  }   

  if (mouseX > 1350 && mouseX < width && mouseY > 0 && mouseY < 70) {
    overButton_sair = true;
  } else {
    overButton_sair = false;
  }
}

//ativar a musica quando o botao for primido
void ativar_botao() {

  if (overButton) { 
    background(200);
    start=1;
    frameCount=0;

    player = minim.loadFile("jumper.mp3");
    player.play();

    //launch("/Users/admin/Desktop/projeto_final/Visita a Casa do Conhecimento.mp4");
  }
}
//pressionar o botao sair
void ativar_saida() {

  if (overButton_sair) { 
    jogo=1;
    frameCount=0;

    //launch("/Users/admin/Desktop/projeto_final/Visita a Casa do Conhecimento.mp4");
  }
}
