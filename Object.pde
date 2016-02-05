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
  
  void paint(){}
}