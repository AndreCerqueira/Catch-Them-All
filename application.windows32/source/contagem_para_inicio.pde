
//contagem decrescente da partida

void cont() {
  background(255);
  fill(0);
  textSize(50);

  int wait = 1000;

  if (millis() - time >= wait) {
    time = millis();
    cont_inicio = cont_inicio - 1;
  }

  pushMatrix();
    scale(1, -1);
    rotate(PI);
    image(regras, 0, 0, width, height);
  popMatrix();
  
  pushMatrix();
    scale(1, -1);
    rotate(PI);
    textSize(500);
     fill(0,150);
    text(cont_inicio, (width/2)-200,( height/2)+250);
  popMatrix();

  if (cont_inicio<=0) {
    contagem=1;
    frameCount = 0;
  }
}                  
