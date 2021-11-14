// Daniel Shiffman

// http://codingtra.in

// http://patreon.com/codingtrain

// Code from: https://youtu.be/nCVZHROb_dE

void computer_vision() {  
  //localizar a cor da "luva" e o seu diametro de alcance
  avgX = 0;

  avgY = 0;

  int count = 0;

  for (int x = 0; x < video.width; x++ ) {

    for (int y = 0; y < video.height; y++ ) {

      int loc = x + y * video.width;

      // What is current color

      color currentColor = video.pixels[loc];

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

float distSq(float x1, float y1, float z1, float x2, float y2, float z2) {

  float d = (x2-x1)*(x2-x1) + (y2-y1)*(y2-y1) +(z2-z1)*(z2-z1);

  return d;
}

void luva() {
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
