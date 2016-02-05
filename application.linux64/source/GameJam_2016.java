import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.effects.*; 
import ddf.minim.signals.*; 
import ddf.minim.spi.*; 
import ddf.minim.ugens.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class GameJam_2016 extends PApplet {








enum Stages{menu, lvl1};
Stages stage;

public Minim minim;
public AudioPlayer musicPlayer;

Handler handler;

public void settings(){
  //size(width, height);
  size(1024, 512);
}

public void setup(){
  listen();
  handler = new Handler();
  minim = new Minim(this);
  stage = Stages.menu;
  frameRate(45F);
  menu();
}


public void draw(){
  background(255);
  mouseListener();
  if (stage == Stages.menu){
    menuGUI.paint();
  }
  else if(stage == Stages.lvl1){
    gameGUI.paint();
  }
} 
class Button extends Sprite{
  
  private String message = "";
   
  private MessageListener messageListener;
 
  Button(int x, int y, int w, int h, int frames, String path){
    super(x, y, w, h, frames, path);
    ButtonsListener.add(this);
    messageListener = new MessageListener(this);
  }

  public boolean mouseOver(){
    if (this.getX() < mouseX && mouseX < this.getX() + this.getW() && this.getY() < mouseY && mouseY < this.getY() + this.getH()){
      return true;
    }
    else return false;
  }
  
  public String getMessage(){
   return this.message; 
  }
  
  public void setMessage(String message){
    this.message = message;
  }
  
  public void sendMessage(String message){
    messageListener.sendMessage(message);
  }
   
  public void newMessage(String message){}
  
}
class GUI{

  private HashMap <Integer, ArrayList<Object> > objectsInLayers;

  GUI(){
    objectsInLayers = new HashMap<Integer, ArrayList<Object>>();
  }

  public void attach(Object object, int layer){
    if(!this.objectsInLayers.containsKey(layer)) this.objectsInLayers.put(layer, new ArrayList<Object>());
    (this.objectsInLayers.get(layer)).add(object);
  }
  
  public ArrayList<Object> getLayerObjects(int layer){
    return (this.objectsInLayers).get(layer);
  }
  
  public void paint(){
    for(int key : (this.objectsInLayers).keySet()){
      for(Object object : (this.objectsInLayers).get(key)){
        object.paint(); 
      }
    }
  }
  
}
class Handler extends Object{
  
  private MessageListener messageListener;
  
  Handler(){
    messageListener = new MessageListener(this);
  }
    
  public void newMessage(String message){
    if (message.equals("lvl1")) {
      loadLevel(1);
      stage = Stages.lvl1;
    }
  }
  
}
class Level extends Sprite{
  
  private ArrayList<Zone> zones = new ArrayList<Zone>();
  private ArrayList<Portal> portals = new ArrayList<Portal>();
 
  Level(){
    super(0, 0, 1, 1, 1, "orange_zone.png");  
    this.setVisible(false);
  }
 
  Level(int x, int y, int w, int h, int frames, String path, ArrayList<Zone> zones, ArrayList<Portal> portals){
    super(x, y, w, h, frames, path);
    for(Zone zone : zones) this.zones.add(zone); 
    for(Portal portal : portals) this.portals.add(portal); 
  }
  
  @Override
  public void paint(){     
    copy(super.img, super.currentX, 0, super.imgW, super.imgH, this.getX(), this.getY(), super.w, super.h);
    
    this.update();
    for(Zone zone : zones){
      zone.paint(); 
    }
    for(Portal portal : portals){
      portal.paint(); 
    }
  }
  
}
class LevelObjects{
  private HashMap<Integer, ArrayList<Zone>> levelsZone;
  private HashMap<Integer, ArrayList<Portal>> levelsPortal;
  
  LevelObjects(){
    levelsZone = new HashMap<Integer, ArrayList<Zone>>();
    levelsPortal = new HashMap<Integer, ArrayList<Portal>>();
    
    levelsZone.put(0, new ArrayList<Zone>());
    levelsZone.put(1, new ArrayList<Zone>());  
    (levelsZone.get(1)).add(new Zone(0, height-34, width, height, zoneType.black)); //Floor, it's neccessary for every level
    (levelsZone.get(1)).add(new Zone(745, 128, 865, height-34, zoneType.black));
    (levelsZone.get(1)).add(new Zone(0, 270, 10, 305, zoneType.black));
    (levelsZone.get(1)).add(new Zone(476, 270, 745, 305, zoneType.black));
    (levelsZone.get(1)).add(new Zone(87, 270, 419, 305, zoneType.black));
    (levelsZone.get(1)).add(new Zone(478, 128, 687, 147, zoneType.black));
    (levelsZone.get(1)).add(new Zone(419, 265, 478, height-34, zoneType.blue));
    (levelsZone.get(1)).add(new Zone(0, 0, 478, 270, zoneType.yellow));
    (levelsZone.get(1)).add(new Zone(478, 0, width, 128, zoneType.yellow));
    (levelsZone.get(1)).add(new Zone(478, 147, 687, 270, zoneType.green));
    (levelsZone.get(1)).add(new Zone(687, 124, 747, 274, zoneType.orange));
    (levelsZone.get(1)).add(new Zone(10, 270, 87, 305, zoneType.red));
    
    
    levelsPortal.put(0, new ArrayList<Portal>());
    levelsPortal.put(1, new ArrayList<Portal>());  
    (levelsPortal.get(1)).add(new Portal(250, height-130, 64, 100, portalType.blue_orange));
    (levelsPortal.get(1)).add(new Portal(600, height-130, 64, 100, portalType.red_green));
    (levelsPortal.get(1)).add(new Portal(130, 170, 64, 100, portalType.blue_orange));
    (levelsPortal.get(1)).add(new Portal(280, 170, 64, 100, portalType.yellow_purple));
    (levelsPortal.get(1)).add(new Portal(130+370, 170, 64, 100, portalType.blue_orange));
    (levelsPortal.get(1)).add(new Portal(280+370, 170, 64, 100, portalType.yellow_purple));
  }
  
  public ArrayList<Zone> getLevelZones(int level){
    return this.levelsZone.get(level);
  }
  
  public ArrayList<Portal> getLevelPortals(int level){
    return this.levelsPortal.get(level); 
  }
  
}  
ArrayList <Button> ButtonsListener;
ArrayList <Handler> PostBoxes;

public void listen(){
  ButtonsListener = new ArrayList<Button>();
  PostBoxes = new ArrayList<Handler>();
}

public void mouseListener(){
    if(mousePressed){
      for (Button button : ButtonsListener){
        if (button.mouseOver()){
          button.sendMessage(button.getMessage());
        }
      } 
    }
}
class MessageListener{
  
  MessageListener(Handler handler){
    PostBoxes.add(handler);
  }
  
  MessageListener(Button button){
  }
  
  public void sendMessage(String message){
    for (Handler handler : PostBoxes){
      handler.newMessage(message);
    }
  }
  
}
abstract class Object {
  
  private String name;
  
  private int x;
  private int y;
  
  private int layer;
  
  Object(){}
  
  Object(int x, int y){
    this.name = "NoName";
    this.x = x; 
    this.y = y;
    this.layer = 0;
  }
  
  public int getX(){
    return x;
  }
  
  public int getY(){
    return y;
  }
  
  public String getName(){
    return this.name;
  }
  
  public int getLayer(){
   return this.layer;
  }
  
  public void setXY(int x, int y){
    this.x = x;
    this.y = y;
  }
  
  public void setX(int PositionX){
    this.x = PositionX;
  }
  
  public void setY(int PositionY){
    this.y = PositionY;
  }

  public void setLayer(int layer){
   this.layer = layer;
  }
  
  public void setName(String name){
    this.name = name;
  }
  
  public void paint(){}
}
class Player extends Sprite {

  private boolean moveUp = false, 
    moveDown = false, 
    moveRight = true, 
    moveLeft = true, 
    gravitationEnabled = true;
  int speed = 5;

  Player(int x, int y, int w, int h, int frames, String path) {
    super(x, y, w, h, frames, path);
  }

  public boolean canMoveUp() {
    return this.moveUp;
  }

  public boolean canMoveDown() {
    return this.moveDown;
  }

  public boolean canMoveRight() {
    return this.moveRight;
  }

  public boolean canMoveLeft() {
    return this.moveLeft;
  }

  public boolean isGravitationEnabled() {
    return this.gravitationEnabled;
  }

  public void setMove(boolean moveUp, boolean moveDown, boolean moveRight, boolean moveLeft) {
    this.moveUp = moveUp;
    this.moveDown = moveDown;
    this.moveRight = moveRight;
    this.moveLeft = moveLeft;
  }

  public void setGravitationEnabled(boolean gravitationEnabled) {
    this.gravitationEnabled = gravitationEnabled;
  }

  public void setMoveUp(boolean moveUp) {
    this.moveUp = moveUp;
  }

  public void setMoveDown(boolean moveDown) {
    this.moveDown = moveDown;
  }

  public void setMoveRight(boolean moveRight) {
    this.moveRight = moveRight;
  }

  public void setMoveLeft(boolean moveLeft) {
    this.moveLeft = moveLeft;
  }

  public void moveX(int step) {
    if (!this.canMoveRight() && step > 0) return;
    if (!this.canMoveLeft() && step < 0) return;
    this.setX(this.getX() + step); 
    if (this.getX() < 0 || this.getX()+this.getW() > width) {
      this.setX(this.getX() - step); 
      return;
    }
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      if (this.getX()+this.getW() > zone.getX1() && this.getX() < zone.getX1() && this.getY() >= zone.getY1() && this.getY() < zone.getY2() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step > 0) {
        this.setX(this.getX() - step); 
        break;
      }
      if (this.getX() < zone.getX2() && this.getX()+this.getW() > zone.getX2() && this.getY() < zone.getY2() && this.getY() >= zone.getY1() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step < 0) {
        this.setX(this.getX() - step); 
        break;
      }
    }
  }

  public void moveY(int step) {
    if (!this.canMoveUp() && step < 0) return;
    if (!this.canMoveDown() && step > 0) return;
    this.setY(this.getY() + step); 
    if (this.getY() < 0 || this.getY()+this.getH() > height) {
      this.setY(this.getY() - step); 
      return;
    }
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      if (this.getY() < zone.getY2() && this.getY() + this.getH() > zone.getY2() && this.getX() >= zone.getX1() &&  this.getX() < zone.getX2() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step < 0) {
        this.setY(this.getY() - step);
        break;
      }
      if (this.getY() + this.getH() > zone.getY1() && this.getY() < zone.getY1() && this.getX() >= zone.getX1() &&  this.getX() < zone.getX2() && (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.black) && step > 0) {
        this.setY(this.getY() - step);
        break;
      }
    }
  }

  public void newMessage(String message) {
    if (message.equals("left")) {
      moveX(-10);
    } else if (message.equals("right")) {
      moveX(10);
    };
  }

  public void gravitation() {
    this.setY(this.getY() + 10);
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      //if(this.getY()+this.getH() >= zone.getY1() && (this.getX() >= zone.getX1() || this.getX()+this.getW() >= zone.getX2()) ){
      if (this.getY() > zone.getY1()) continue;
      if (( (this.getX() > zone.getX1() && this.getX() < zone.getX2()) || (this.getX()+this.getW() < zone.getX2() && this.getX()+this.getW() > zone.getX1())) && this.getY()+this.getH() >= zone.getY1() && (zone.getZoneType() == zoneType.black || zone.getZoneType() == zoneType.blue || zone.getZoneType() == zoneType.green) ) {
        this.setY(this.getY() - 10); 
        break;
      }
    }
  }

  @Override
    public void controls() {
    if(this.getX() >= shrine.getX() && this.getY()+this.getH() > shrine.getY()){
      noLoop();
      fill(255, 0, 0);
      textSize(144);
      text("GG m8", 100, 150);
    }
    if (this.isGravitationEnabled()) gravitation();
    if (keyPressed) {
      if (key == 'a' || key == 'A') this.moveX(-5);
      else if (key == 'd' || key == 'D') this.moveX(5);
      else if (key == CODED) {
        if (keyCode == LEFT) this.moveX(-5);
        else if (keyCode == RIGHT) this.moveX(5);
      }
    }
  }

  @Override 
    public void overObject() {
    for (Portal portal : levelObjects.getLevelPortals(currentLevel)) {
      if (portal.isUsed()) continue;
      if (keyPressed && key == 32 && this.getX()+this.getW()/2 >= portal.getX() && this.getX()+this.getW()/2 < portal.getX()+portal.getW() && this.getY()+this.getH()/2 > portal.getY() && this.getY()+this.getH()/2 < portal.getY()+portal.getW()) {  
        for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
          switch(portal.getPortalType()) {
          case red_green:
            if (zone.getZoneType() == zoneType.green || zone.getZoneType() == zoneType.red)
              zone.invert();
            break;
          case yellow_purple:
            if (zone.getZoneType() == zoneType.yellow || zone.getZoneType() == zoneType.purple)
              zone.invert();
            break;
          case blue_orange:
            if (zone.getZoneType() == zoneType.blue || zone.getZoneType() == zoneType.orange)
              zone.invert();
            break;
          }
        }
        portal.setUsed();
      }
    }

    //println(this.getX() + "  " + (this.getY()+this.getH()));
    this.setMoveUp(true);
    this.setMoveDown(true);
    this.setMoveRight(true);
    this.setMoveLeft(true);
    this.setGravitationEnabled(true);
    for (Zone zone : levelObjects.getLevelZones(currentLevel)) {
      if (this.getX() > zone.getX1() && this.getX()+this.getW() < zone.getX2() && this.getY()+this.getH()-2 < zone.getY2() && this.getY()+this.getH()-2 > zone.getY1()) {
        this.speed = 5;
        println(zone.getZoneType());
        switch(zone.getZoneType()) {
        case blue:
          this.setGravitationEnabled(false);
          this.setMoveUp(true);
          this.setMoveDown(false);
          this.setMoveRight(false);
          this.setMoveLeft(false);
          this.moveY(-5);
          return;
        case orange:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(true);
          this.setMoveLeft(true);
          return;
        case yellow:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(false);
          this.setMoveLeft(true);
          return;
        case purple:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(true);
          this.setMoveLeft(false);
          return;
        case red:
          this.setMoveUp(false);
          this.setMoveDown(true);
          this.setMoveRight(true);
          this.setMoveLeft(true);
          return;
        case green:
          this.setMoveUp(false);
          this.setMoveDown(false);
          this.setMoveRight(false);
          this.setMoveLeft(false);
          return;
        case black:
          this.setMoveUp(false);
          this.setMoveDown(false);
          this.setMoveRight(false);
          this.setMoveLeft(false);
          return;
        }
      }
    }
  }
}
public enum portalType {blue_orange, yellow_purple, red_green};

class Portal extends Sprite{

  portalType type;
  
  private boolean used = false;
   
  Portal(int x, int y, int w, int h, portalType type){ 
    super(x, y, w, h, 1, "yellow_zone.png");
    this.type = type;
    switch(type){
      case blue_orange:
        this.loadImg(w, h, 1, "blue_orange_portal.png");
        break;
        
      case yellow_purple:
        this.loadImg(w, h, 1, "yellow_purple_portal.png");
        break;
        
      case red_green:
        this.loadImg(w, h, 1, "red_green_portal.png");
        break;
    } 
  }

  public boolean isUsed(){
    return this.used;
  }
  
  public void setUsed(){
    this.used = true;
    this.setVisible(false);
  }

  public portalType getPortalType(){
    return this.type;
  }
}
class Sprite extends Object{
  
  private int currentX,
              h, w,
              imgH, imgW,
              frames, currentFrame;
  private PImage img;
  private boolean visible, paused;
  private ArrayList<PImage> framesList;

  Sprite(int x, int y, int w, int h, int frames, String path){
    super(x, y);
    framesList = new ArrayList<PImage>(); 
    this.loadImg(w, h, frames, path);
  }
  
  public void loadImg(int w, int h, int frames, String path){
    this.w = w;
    this.h = h;   
    this.frames = frames;
    this.img = loadImage(path);
    this.imgH = img.height;
    this.imgW = img.width/frames;
    this.currentFrame = 0;
    this.visible = true;
    this.setLayer(0);
    fillFrames(w, h, this.img, frames);
  }
  
  public void fillFrames(int w, int h, PImage img, int frames){
    for(int i = 0; i < frames; ++i){
      this.framesList.add(i, img.get(i*this.imgW, 0, this.imgW, this.imgH));     
      this.framesList.get(i).resize(w, h); 
    }
  }

  public int getW(){
    return this.w;
  }
  
  public int getH(){
    return this.h;
  }
  
  public boolean isVisible(){
    return this.visible;
  }

  public boolean isPaused(){
    return this.paused;
  }
  
  public void setVisible(boolean visible){
    this.visible = visible;
  }
  
  public void setPaused(boolean paused){
    this.paused = paused;
  }

  public void setFrame(int frame){
     this.currentFrame = frame;
     this.currentX = this.currentFrame * this.imgW;
  }

  public void update(){
    if(paused) return;
    this.currentFrame = this.currentFrame+1 == this.frames ? 0 : ++this.currentFrame;
    this.currentX = this.currentFrame * this.imgW;
  }
 
  public void paint(){  
    if(this.visible){
      image(this.framesList.get(this.currentFrame), this.getX(), this.getY());
      
      this.overObject();
      this.controls();
      
      this.update();
    }
  }

  public void controls(){}
  public void overObject(){}
  
}

public enum zoneType {blue, orange, yellow, purple, red, green, black};

class Zone extends Sprite{

  zoneType type;
  public int x1, x2, y1, y2;
   
  Zone(int x1, int y1, int x2, int y2, zoneType type){ 
    super(x1, y1, (x2-x1), (y2-y1), 1, "yellow_zone.png");
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.type = type;
    this.setSprite(this.type);
  }
  
  public void setSprite(zoneType type){
    switch(type){
      case blue:
        this.loadImg((x2-x1), (y2-y1), 33, "blue_zone.png");
        break;
        
      case orange:
        this.loadImg((x2-x1), (y2-y1), 1, "orange_zone.png");
        break;
        
      case yellow:
        this.loadImg((x2-x1), (y2-y1), 1, "yellow_zone.png");
        break;
      
      case purple:
        this.loadImg((x2-x1), (y2-y1), 1, "purple_zone.png");
        break;
        
      case red:
        this.loadImg((x2-x1), (y2-y1), 1, "red_zone.png");
        break;
        
      case green:
        this.loadImg((x2-x1), (y2-y1), 1, "green_zone.png");
        break;
        
      case black:
        this.loadImg((x2-x1), (y2-y1), 1, "black_zone.png");
        this.setVisible(false);
        break;
    } 
  }
  
  public int getX1(){
    return this.x1; 
  }
  
  public int getX2(){
    return this.x2; 
  }
  
  public int getY1(){
    return this.y1; 
  }
  
  public int getY2(){
    return this.y2; 
  }
  
  public zoneType getZoneType(){
    return this.type;
  }
  
  public void setZoneType(zoneType type){
    this.type = type;
  }
  
  public void invert(){
    switch(this.getZoneType()){
      case blue:
        this.loadImg((x2-x1), (y2-y1), 1, "orange_zone.png");
        this.setZoneType(zoneType.orange);
        break;
        
      case orange:
        this.loadImg((x2-x1), (y2-y1), 33, "blue_zone.png");
        this.setZoneType(zoneType.blue);
        break;
        
      case yellow:
        this.loadImg((x2-x1), (y2-y1), 1, "purple_zone.png");
        this.setZoneType(zoneType.purple);
        break;
      
      case purple:
        this.loadImg((x2-x1), (y2-y1), 1, "yellow_zone.png");
        this.setZoneType(zoneType.yellow);
        break;
        
      case red:
        this.loadImg((x2-x1), (y2-y1), 1, "green_zone.png");
        this.setZoneType(zoneType.green);
        break;
        
      case green:
        this.loadImg((x2-x1), (y2-y1), 1, "red_zone.png");
        this.setZoneType(zoneType.red);
        break;
      
      case black:
        break;
    }
  }
}
public GUI gameGUI;

public Sprite shrine;
public Player player;
public LevelObjects levelObjects;
public ArrayList<Level> levels;
public int currentLevel;

public void loadLevel(int level){
  levels = new ArrayList<Level>();
  levelObjects = new LevelObjects();
  gameGUI = new GUI();
  currentLevel = level;
  levels.add(0, new Level());
  //Background
  levels.add(level, new Level(0, 0, width, height, 1, "level_1_background.png", levelObjects.getLevelZones(level), levelObjects.getLevelPortals(level)));
  gameGUI.attach(levels.get(level), 1);
  //Background
 
  //Player
  player = new Player(20, height-100-50, 53, 100, 18, "player.png");
  //player.setPaused(true);
  //player.setFrame(6);
  gameGUI.attach(player, 10); //Player must be on top
  //Player
  
  shrine = new Sprite(width - 120, height-100-35, 75, 100, 1, "shrine.png");
  
  gameGUI.attach(shrine, 1);
  
  musicPlayer = minim.loadFile("Main_test4.mp3");
  musicPlayer.play();
  musicPlayer.loop();
}
public GUI menuGUI;

public void menu(){
  
 menuGUI = new GUI(); 
 Sprite background = new Sprite(0,0,1024,512,1,"menu.png");
 Button lvl1 = new Button(width/2-165, 350, 256, 128, 1, "play.png");
 lvl1.setMessage("lvl1");
 
 menuGUI.attach(lvl1, 2);
 menuGUI.attach(background, 1);
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "GameJam_2016" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
