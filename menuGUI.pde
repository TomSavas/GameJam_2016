public GUI menuGUI;

void menu(){
  
 menuGUI = new GUI(); 
 Sprite background = new Sprite(0,0,1024,512,1,"menu.png");
 Button lvl1 = new Button(width/2-165, 350, 256, 128, 1, "play.png");
 lvl1.setMessage("lvl1");
 
 menuGUI.attach(lvl1, 2);
 menuGUI.attach(background, 1);
}