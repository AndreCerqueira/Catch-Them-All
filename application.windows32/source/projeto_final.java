import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.video.*; 
import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class projeto_final extends PApplet {


  // importar biblioteca de video
         // importar biblioteca de som 

AudioPlayer player; //audio
Minim minim;     

//[ Variaveis ]//

boolean overButton = false, overButton_sair = false;   // esta variavel e para saber se o x e o y esta por cima

int start, contagem, jogo;  //start é para o programa apresentar o primeiro menu ////contagem é para o programa apresentar o segundo menu ////jogo é para o programa apresentar o terceiro menu; 

int score, maior_score=1075; // <------ Maior Score

PImage regras, logo, ladrao; 

PImage bomb, diamante, moeda, botao;

bola[] b = new bola[6];   // quantidade de objetos que caem no jogo

float avgX = 0, avgY = 0;    //x e y da mao/ladrao do jogador

float threshold = 25;

Capture video;

int time; 
int cont=60;   // contador de tempo da partida

int cont_inicio=5;  // contador de tempo para mostrar as instruçoes

int cont_fim=5;  // contador de tempo para reiniciar a partida




public void setup() {
  
  noCursor();

    //[ Carregar todas as imagens ]//

    regras=loadImage("regras.jpg");
  ladrao=loadImage("mocho.png");

  logo=loadImage("logo.png");
  moeda=loadImage("pen.png");
  bomb=loadImage("virus.png");
  diamante=loadImage("computador.png");

  //[ Atribuir cada parte do array como objeto bola ]//

  for (int i=0; i<b.length; i++) {
    b[i] = new bola();
  }

  //String[] cameras = Capture.list();

  //printArray(cameras);

  //[ Começar o video ]//

  video = new Capture(this, 640, 480, 30);
  video.start();


  minim = new Minim(this);  // audio

  time = millis();  //tempo
}


public void captureEvent(Capture video) {
  video.read();
}


public void draw() {

  //image(video, 0, 200, width, height);    // video
  //video.loadPixels();   

  scale(-1, 1);        // inverter o ecra 

  //image(video, -video.width-1280, 0, width, height);
  //filter(BLUR, 6);
  video.loadPixels();   

  background(0);

  //image(imagem_fundo, 0, 0, width, height);    // imagem de fundo (faz lag)

  noStroke();

  // 1º parte do jogo (botao de iniciar) 

  if (start==0) {

    botao();

    checkButton();
    ativar_botao();
  }

  // 2º parte do jogo (instruçoes) 

  if ((contagem==0) && (start==1)) {
    cont();
  }

  // 3º parte do jogo (jogar) 

  if ((jogo==0) && (contagem==1) && (start==1)) {


    bolas_em_queda();

    menu();
    contagem_da_partida();
  }

  // 4º parte do jogo (mostrar score, fim)                                                

  if (jogo==1) {
    fim(); 
    fill(255, 100);
  }

  creditos();

  computer_vision();
  luva();
}



public void mousePressed() {
  ativar_saida();
}

public void mouseMoved() {
  checkButton();
}

//void stop()
//{
//  player.close();
//  minim.stop();
//  super.stop();
//}
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
  public void forma() {
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

  public void movimento() {
    y=y+yspeed;
    //yspeed=yspeed+0.15;
  }

  //fazer a bola desaparecer e depois aparecer num c random em y=0
  public void fim_queda() {

    if ((y>=height+diam) || (explosao == 1)) {
      y=random(-50, -300);
      yspeed = random(5, 10);  
      
      x=random(-width, 0);

      percentagem = random(0, 100);

      explosao=0;
    }
  }
  //pontuaçao
  public void explosao() {

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
  public void jogo_reset() {

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
public void bolas_em_queda() {  

  //bolas a cair
  for (int i=0; i<b.length; i++) {
    b[i].forma();

    b[i].movimento();
    b[i].fim_queda();

    b[i].explosao();
  }
}
// Daniel Shiffman

// http://codingtra.in

// http://patreon.com/codingtrain

// Code from: https://youtu.be/nCVZHROb_dE

public void computer_vision() {  
  //localizar a cor da "luva" e o seu diametro de alcance
  avgX = 0;

  avgY = 0;

  int count = 0;

  for (int x = 0; x < video.width; x++ ) {

    for (int y = 0; y < video.height; y++ ) {

      int loc = x + y * video.width;

      // What is current color

      int currentColor = video.pixels[loc];

      float r1 = red(currentColor);

      float g1 = green(currentColor);

      float b1 = blue(currentColor);

      float r2 = red(-13934796);

      float g2 = green(-13934796);

      float b2 = blue(-13934796);



      float d = distSq(r1, g1, b1, r2, g2, b2); 



      if (d < threshold*threshold) {

        avgX = avgX + x;

        avgY = avgY + y;

        count++;
      }
    }
  }

  if (count > 0) { 

    avgX = avgX / count*3;

    avgY = avgY / count*2;

    //fill(0,255,0);
  }
}

public float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {

  float d = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) +(z2-z1)*(z2-z1);

  return d;
}

public void luva() {
  //diametro de alcance

  //strokeWeight(4.0);
  //stroke(0);

  pushMatrix();
  scale(-1, -1);
  rotate(PI);
  noStroke();
  noFill();
  image(ladrao, avgX-width-80, avgY+50, 340, 290);
  popMatrix();
}

//George Profenza

//contar o tempo da partida
public void contagem_da_partida() {

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

//contagem decrescente da partida

public void cont() {
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

public void fim() {
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




public void creditos() {
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
//menu de cima da partida

public void menu() {
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
//botao de inicio

public void botao() {
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

public void checkButton() {
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
public void ativar_botao() {

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
public void ativar_saida() {

  if (overButton_sair) { 
    jogo=1;
    frameCount=0;

    //launch("/Users/admin/Desktop/projeto_final/Visita a Casa do Conhecimento.mp4");
  }
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--hide-stop", "projeto_final" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
