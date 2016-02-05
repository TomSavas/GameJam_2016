
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
  
  void setSprite(zoneType type){
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