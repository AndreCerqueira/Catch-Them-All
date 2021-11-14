
//George Profenza

//contar o tempo da partida
void contagem_da_partida() {

  if ((jogo==0) && (contagem==1) && (start==1)) {

    int wait = 1000;

    textSize(100);
    fill(255);

    if (millis() - time >= wait) {
      time = millis();
      cont = cont - 1;
    }


    if (cont==60) {
      pushMatrix();
        scale(1, -1);
        rotate(PI);
        text("1:00", 1600, 100);
      popMatrix();     
    } else if (cont<10) {
      pushMatrix();
        scale(1, -1);
        rotate(PI);
        text("0:0"+cont, 1600, 100);
      popMatrix();  
    } else {
      pushMatrix();
        scale(1, -1);
        rotate(PI);
        text("0:"+cont, 1600, 100);
      popMatrix(); 
    }

    if (cont<0) {
      jogo=1;
      frameCount = 0;
    }
  }
}
