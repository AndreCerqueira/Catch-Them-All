
void fim() {
  //pontuaçao
  
  background(0);
 pushMatrix();
    scale(1, -1);
    rotate(PI);
  textSize(250);
  fill(255);
  text("GAME OVER", 220, 475);

  textSize(80);
  text("Score: "+score, 780, 675);

  if (score>=maior_score) {
    maior_score=score;

    textSize(150);
    fill(255, 255, 0);
    text("NEW RECORD", 490, 975);
  }
   popMatrix();

  

//restart

  textSize(40);
  fill(255);

//contagem decrescente para o restart

  int wait = 1000;

  if (millis() - time >= wait) {
    time = millis();
    cont_fim = cont_fim - 1;
  }
 pushMatrix();
    scale(1, -1);
    rotate(PI);
    textSize(150);
  text(cont_fim,70, 150);
 popMatrix();
  if (cont_fim<=0) {
    for (int i=0; i<b.length; i++) {
      b[i].jogo_reset();

      player.close();
      minim.stop();
    }
  }
}




void creditos() {
//logo
  tint(255, 127);
  image(logo, width-100, 980, 100, 100);
  noTint();

  //textSize(20);
  //fill(255, 100);
  //text("Feito por: ", width-170, 1020);
  //text("André Cerqueira", width-170, 1040);
  //text("Paulo Viana", width-170, 1060);
  //image(logo, width-100, -5, 100, 100);
}
