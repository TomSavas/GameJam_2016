import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.effects.*;
import ddf.minim.signals.*;
import ddf.minim.spi.*;
import ddf.minim.ugens.*;

enum Stages{menu, lvl1};
Stages stage;

public Minim minim;
public AudioPlayer musicPlayer;

Handler handler;

void settings(){
  //size(width, height);
  size(1024, 512);
}

void setup(){
  listen();
  handler = new Handler();
  minim = new Minim(this);
  stage = Stages.menu;
  frameRate(45F);
  menu();
}


void draw(){
  background(255);
  mouseListener();
  if (stage == Stages.menu){
    menuGUI.paint();
  }
  else if(stage == Stages.lvl1){
    gameGUI.paint();
  }
} 