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