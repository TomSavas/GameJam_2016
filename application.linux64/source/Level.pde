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