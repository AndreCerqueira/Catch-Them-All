class bola {
  float percentagem; //variavel da percentagem em que cada peça (moeda,bomba,diamante) aparece

  float xspeed, yspeed, x, y; //variaveis da velocidade

  float diam, explosao; //variaveis para o desaparecimento das peças e o seu diametro

  float overlaps; //variavel para saber se uma peça esta sobreposta por outra

  bola() { 
    percentagem = random(0, 100);   // 10- diamante  50-moeda  40-bomba

    x=random(-width, 0);
    y=random(-50, -300);

    //velocidade
    yspeed = random(5, 10);  

    xspeed=0;
    //yspeed=10;

    noStroke();
  }

  //saber qual peça vai aparecer
  void forma() {
    //pushMatrix();
    //   scale(1, -1);
    //   rotate(PI);
    if (percentagem<50) {      
      diam = 220;
      image(moeda, x, y, diam, diam-40);
    } else if (percentagem<90) {
      diam = 210;
      image(bomb, x, y, diam, diam);
    } else if (percentagem<100) {
      diam = 150;
      image(diamante, x, y, diam, diam);
    }
    //popMatrix();
  }

  void movimento() {
    y=y+yspeed;
    //yspeed=yspeed+0.15;
  }

  //fazer a bola desaparecer e depois aparecer num c random em y=0
  void fim_queda() {

    if ((y>=height+diam) || (explosao == 1)) {
      y=random(-50, -300);
      yspeed = random(5, 10);  
      
      x=random(-width, 0);

      percentagem = random(0, 100);

      explosao=0;
    }
  }
  //pontuaçao
  void explosao() {

    overlaps = dist(x, y, avgX-width, avgY);

    if (overlaps < diam/2+100) {

      if (percentagem<50) {
        score=score+5;
        explosao=1;
      } else if (percentagem<90) {
        score=score-10;
        explosao=1;
      } else if (percentagem<100) {
        score=score+50;
        explosao=1;
      }
    }
  }
  //reset do jogo
  void jogo_reset() {

    x=random(-width, 0);
    y=random(-50, -300);
    yspeed = random(5, 10);  
    

    start=0;
    jogo=0;
    contagem=0;
    score=0;

    cont=60;
    cont_inicio=5;
    cont_fim=5;
  }
}
