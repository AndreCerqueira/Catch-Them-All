
import processing.video.*;  // importar biblioteca de video
import ddf.minim.*;         // importar biblioteca de som 

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




void setup() {
  fullScreen();
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


void captureEvent(Capture video) {
  video.read();
}


void draw() {

  //image(video, 0, 200, width, height);    // video
  //video.loadPixels();   

  scale(-1, 1);        // inverter o ecra 

  image(video, -video.width-1280, 0, width, height);
  //filter(BLUR, 6);
  video.loadPixels();   

  //background(0);

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



void mousePressed() {
  ativar_saida();
}

void mouseMoved() {
  checkButton();
}

//void stop()
//{
//  player.close();
//  minim.stop();
//  super.stop();
//}
